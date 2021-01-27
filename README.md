# EbayAutoDisplay

Use Ebay finding API easily with this libarary
### Samples
|   |   |
|:-:|:-:|
 <img  src="/sample4.gif" alt="Example 1" width="200" style="max-width:100%;"> | <img  src="/sample5.gif" alt="Example 1" width="200" style="max-width:100%;"> |
| <img src="/sample6.gif" alt="Example 1" width="200" style="max-width:100%;"> 



[![](https://jitpack.io/v/BarOvda/CircularRatingWithAvatar.svg)](https://jitpack.io/#BarOvda/CircularRatingWithAvatar/1.1)

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
	        implementation 'com.github.BarOvda:'
	}
	
	
	
## XML
```
   <com.example.ebayautodisplayllibrary.EbayDisplayFragment
        android:id="@+id/ebay_element"
        android:layout_width="384dp"
        android:layout_height="485dp"
        android:layout_marginStart="16dp"
        android:layout_marginLeft="16dp"
        android:layout_marginTop="56dp"
        app:layout_constraintTop_toBottomOf="@+id/textView"
        app:layout_constraintStart_toStartOf="parent"
        
        app:view_style="modern"
        app:text_color="@color/black"
        app:view_background_color="@drawable/example_style"
        app:card_background_color="@drawable/example_style"
        app:display_view_orientation="vertical"
       >

    </com.example.ebayautodisplayllibrary.EbayDisplayFragment>

```
Here is the list of properties you can use to configure this view:
 1. app:view_style (enum {classic,modern})  ==> sets the style of the card views content (deafult value:"classic")
 2. app:text_color (color) ==> sets the text color (deafult value:Color.BLACK)
 3. app:view_background_color (drawable)  ==> sets the background of the frame (deafult value:Color.WHITE)
 4. app:card_background_color (color) ==> sets the background of the card views (deafult value:Color.WHITE)
 5. app:display_view_orientation (enum {vertical,horizontal}) ==> sets the orientation of the LayoutManager (deafult value:"vertical")

## JAVA
```
   ebayDisplayFrameLayout.initEbayAutoDisplay(EBAY_APP_ID,"Apple iPhone 8",9355,mLocation);
```
Here is the list of properties you can use to configure this method:
 1. ebayAppID (String)  ==> your application api ID
    you can generate ID with this link https://developer.ebay.com/tools/quick-start
 2. keyWord (String) ==> the search key word
 3. categoryId (int)  ==> search by a specific category (optional).
    for the full categories list https://pages.ebay.com/sellerinformation/news/fallupdate16/category-and-item.html
 4. location (Location) ==> search by the closest sellers (optional)

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
 
