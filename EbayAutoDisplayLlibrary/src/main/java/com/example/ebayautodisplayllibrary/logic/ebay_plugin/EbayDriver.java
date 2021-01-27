package com.example.ebayautodisplayllibrary.logic.ebay_plugin;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ebayautodisplayllibrary.logic.EbayTitle;
import com.example.ebayautodisplayllibrary.logic.IProcess;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class EbayDriver extends AsyncTask<Void, Void, Void> {
    private final static String EBAY_FINDING_SERVICE_URI = "https://svcs.ebay.com/services/search/FindingService/v1?SECURITY-APPNAME="
            + "{applicationId}&OPERATION-NAME={operation}&SERVICE-VERSION={version}"
            +"&RESPONSE-DATA-FORMAT=XML"
            + "&REST-PAYLOAD=true&keywords={keywords}&paginationInput.entriesPerPage={perpage}"
            +"&paginationInput.pageNumber={page}"
            + "&GLOBAL-ID={globalId}&siteid=0"
            ;
    private static final String SERVICE_VERSION = "1.0.0";
    private static final String OPERATION_NAME = /*"findItemsByKeywords"*/"findItemsAdvanced";
    private static final String GLOBAL_ID = "EBAY-US";
    private final static int REQUEST_DELAY = 0;
    private final static int PER_PAGE =16;
    private int perPage;
    private int categoryId;
    private IProcess mProcess;
    private String ebayAppID;
    private String tag;
    private List<EbayTitle> titles;
    private int page;
    private String postalCode;
    public EbayDriver() {
        this.titles = new ArrayList<>();
        this.perPage = PER_PAGE;
    }



    public EbayDriver(Context context,String ebayAppID, String keyWord, int categoryId, Location location, IProcess mProcess) {
        this.titles = new ArrayList<>();
        this.ebayAppID = ebayAppID;
        this.perPage = PER_PAGE;
        this.tag = keyWord;
        this.mProcess = mProcess;
        this.categoryId = categoryId;
        this.page = 1;
        convertLocationToPostalCode(context,location);
    }

    private void convertLocationToPostalCode(Context context,Location location) {
        if(location==null)
            return;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
            int count=0;
            while (this.postalCode == null && count < addresses.size()) {
                this.postalCode = addresses.get(count).getPostalCode();
                    count++;
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPage(int page){
        this.page = page;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try  {

            String address = createAddress(tag);
            Log.d("sending request to :: ", address);
            String response = URLReader.read(address);
            Log.d("response :: ", response);
            //process xml dump returned from EBAY
            processResponse(response);
            //Honor rate limits - wait between results
            Thread.sleep(REQUEST_DELAY);
        } catch (Exception e) {
            e.printStackTrace();
        }        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mProcess.updateAdapter();

    }
    public EbayDriver(int maxResults, String console) {
        this.titles = new ArrayList<>();

        this.perPage = maxResults;
    }




    private String createAddress(String tag) {


        //substitute token
        String address = EBAY_FINDING_SERVICE_URI;
        address = address.replace("{version}", SERVICE_VERSION);
        address = address.replace("{operation}", OPERATION_NAME);
        address = address.replace("{globalId}", GLOBAL_ID);
        address = address.replace("{applicationId}", ebayAppID);
        address = address.replace("{keywords}", tag);
        address = address.replace("{perpage}", "" + this.perPage);
        address = address.replace("{page}", this.page+"");
        if(this.categoryId>0) {
            address +="&categoryId={categoryId}";
            address = address.replace("{categoryId}", this.categoryId+"");
        }
        if(postalCode!=null){
            address +="&buyerPostalCode={postalCode}";
            address +="&sortOrder=Distance";
            address = address.replace("{postalCode}", this.postalCode);
        }else{
            address+="&sortOrder=BestMatch";
        }
        return address;

    }

    private void processResponse(String response) throws Exception {

        XPath xpath = XPathFactory.newInstance().newXPath();
        InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = domFactory.newDocumentBuilder();

        Document doc = builder.parse(is);
        XPathExpression ackExpression = xpath.compile("//findItemsAdvancedResponse/ack");
        XPathExpression itemExpression = xpath.compile("//findItemsAdvancedResponse/searchResult/item");

        String ackToken = (String) ackExpression.evaluate(doc, XPathConstants.STRING);
        Log.d("ACK from ebay API :: ", ackToken);
        if (!ackToken.equals("Success")) {
            throw new Exception(" service returned an error");
        }

        NodeList nodes = (NodeList) itemExpression.evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) {
            EbayTitle tmp = new EbayTitle();
            Node node = nodes.item(i);

            String itemId = (String) xpath.evaluate("itemId", node, XPathConstants.STRING);
            String title = (String) xpath.evaluate("title", node, XPathConstants.STRING);
            String itemUrl = (String) xpath.evaluate("viewItemURL", node, XPathConstants.STRING);
            String galleryUrl = (String) xpath.evaluate("galleryURL", node, XPathConstants.STRING);
            String currentPrice = (String) xpath.evaluate("sellingStatus/convertedCurrentPrice", node, XPathConstants.STRING);

            tmp.setCurrentPrice(currentPrice);
            tmp.setGalleryUrl(galleryUrl);
            tmp.setTitle(title);
            tmp.setItemId(itemId);
            tmp.setItemUrl(itemUrl);
            titles.add(tmp);

            Log.d("currentPrice", currentPrice);
            Log.d("itemId", itemId);
            Log.d("title", title);
            Log.d("galleryUrl", galleryUrl);

        }

        is.close();

    }
    public List<EbayTitle> getTitles(){return this.titles;}


}
