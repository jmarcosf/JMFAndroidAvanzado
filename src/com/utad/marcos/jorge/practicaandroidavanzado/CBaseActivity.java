/**************************************************************/
/*                                                            */ 
/* CBaseActivity.java                                         */ 
/* (c)2014 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CBaseActivity Class                           */ 
/*              Práctica asignatura Android Avanzado          */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: January 2014                                  */ 
/*                                                            */ 
/**************************************************************/
package com.utad.marcos.jorge.practicaandroidavanzado;

import android.os.Bundle;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GAServiceManager;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CBaseActivity Class                                        */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CBaseActivity extends android.support.v4.app.FragmentActivity
{
protected static final String GA_EVENT_CATEGORY_UI_ACTION        = "ui_action";
protected static final String GA_EVENT_CATEGORY_POST_ACTION      = "post_action";

protected static final String GA_EVENT_ACTION_BUTTON_CLICK       = "button_click";
protected static final String GA_EVENT_ACTION_SHAKE_DEVICE       = "shake_device";
protected static final String GA_EVENT_ACTION_MENUITEM_CLICK     = "menuitem_click";
protected static final String GA_EVENT_ACTION_POST_IMAGE         = "post_image";

protected static final String GA_EVENT_ID_PICTURE_IMAGE          = "picture_image";
protected static final String GA_EVENT_ID_MAP_IMAGE              = "map_image";
protected static final String GA_EVENT_ID_FACEBOOK_IMAGE         = "facebook_image";

protected static final String GA_EVENT_MENUITEM_CAPTURE          = "menuitem_capture";
protected static final String GA_EVENT_MENUITEM_TRASH            = "menuitem_trash";
protected static final String GA_EVENT_MENUITEM_SHARE            = "menuitem_image";
protected static final String GA_EVENT_MENUITEM_SETTINGS         = "menuitem_settings";

protected GoogleAnalytics     m_GoogleAnalytics;
protected Tracker             m_Tracker;
     
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Activity Override Methods                             */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CBaseActivity.onCreate()                              */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onCreate( Bundle savedInstanceState )
     {
          super.onCreate( savedInstanceState );
          m_GoogleAnalytics = GoogleAnalytics.getInstance( this.getApplicationContext() );
          m_Tracker = m_GoogleAnalytics.getTracker( getString( R.string.ga_trackingId ) );
     }

     /*********************************************************/
     /*                                                       */ 
     /* CBaseActivity.onStart()                               */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onStart()
     {
          super.onStart();
          EasyTracker.getInstance().activityStart( this );
     }
          
     /*********************************************************/
     /*                                                       */ 
     /* CBaseActivity.onStop()                                */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onStop()
     {
          EasyTracker.getInstance().activityStop( this );
          super.onStop();
     }
     
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Methods                                         */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CBaseActivity.SendEvent()                             */ 
     /*                                                       */ 
     /*********************************************************/
     protected void SendEvent( String Category, String Action, String Label, Long Value )
     {
          m_Tracker.sendEvent( Category, Action, Label, Value );
          GAServiceManager.getInstance().dispatch();   // Send Event right now
     }
}
