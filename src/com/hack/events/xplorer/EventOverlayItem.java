/***
 * Copyright (c) 2011 readyState Software Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.hack.events.xplorer;

import java.util.Date;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class EventOverlayItem extends OverlayItem {

	protected String mImageURL;
	public Event event;
	
	public EventOverlayItem(GeoPoint point, Event event, Date date)
	{ 
		super(point, event.name, date.toString());
		
		mImageURL = event.picUrl;
		this.event = event;
	}

	public String getImageURL() {
		return mImageURL;
	}

	public void setImageURL(String imageURL) {
		this.mImageURL = imageURL;
	}
	
}
