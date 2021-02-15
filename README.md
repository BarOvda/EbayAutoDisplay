# EbayAutoDisplay

Simple way to use Ebay finding API.

With this library, you can find Ebay proudacts by submitted String.

Use Category ID and Location for more specific results.

Use location for nearby sellers view. (Works best with USA and Europe locations)

Choose and custome your own design!


### Samples

#### Example test form

<img  src="/search_sample.PNG" alt="sample 1" width="200" style="max-width:100%;"> 

#### Designs samples
|   |   |
|:-:|:-:|
 <img  src="/sample 1.gif" alt="Example 1" width="200" style="max-width:100%;"> | <img  src="/sample 2.gif" alt="Example 1" width="200" style="max-width:100%;"> 
 |<img src="/sample 3.gif" alt="Example 1" width="200" style="max-width:100%;"> |<img src="/sample 4.gif" alt="Example 1" width="200" style="max-width:100%;"> 



[![](https://jitpack.io/v/BarOvda/EbayAutoDisplay.svg)](https://jitpack.io/#BarOvda/EbayAutoDisplay)

## Setup

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.BarOvda:EbayAutoDisplay:TAG'
	}
	
	
	
## XML
```
   <com.example.ebayautodisplayllibrary.EbayDisplayFragment
        android:id="@+id/ebay_display_frame_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        
        app:view_style="modern"
        app:text_color="@color/black"
        app:view_background_color="@drawable/example_style"
        app:card_background_color="@drawable/example_style"
        app:display_view_orientation="vertical"/>
```
Here is the list of properties you can use to configure this view:
 1. app:view_style (enum {classic,modern})  ==> Sets the style of the card views content (deafult value:"classic")
 2. app:text_color (color) ==> Sets the text color (deafult value:Color.BLACK)
 3. app:view_background_color (drawable)  ==> Sets the background of the frame (deafult value:Color.WHITE)
 4. app:card_background_color (drawable) ==> Sets the background of the card views (deafult value:Color.WHITE)
 5. app:display_view_orientation (enum {vertical,horizontal}) ==> Sets the orientation of the LayoutManager (deafult value:"vertical")

## JAVA
```
   ebayDisplayFrameLayout.initEbayAutoDisplay(EBAY_APP_ID,"Apple iPhone 8",9355,mLocation);
```
Here is the list of properties you can use to configure this method:
 1. ebayAppID (String)  ==> Your application API ID. 
 
    You can generate an ID with this link: <a href="https://developer.ebay.com/tools/quick-start "><img src="https://cdn2.iconfinder.com/data/icons/social-icons-circular-color/512/ebay-512.png" width="30" height="30"/></a>
 
 2. keyWord (String) ==> The search key word
 
 3. categoryId (int)  ==> Search by a specific category (optional). 
 
    For the full categories list: <a href="https://pages.ebay.com/sellerinformation/news/fallupdate16/category-and-item.html"><img src="https://cdn2.iconfinder.com/data/icons/social-icons-circular-color/512/ebay-512.png" width="30" height="30"/></a> 
 
 4. location (Location) ==> Search by the closest sellers (optional)
    The sort order of the results will be by Distance.

## License
 Copyright 2019-2020 Bar Ovda

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 
