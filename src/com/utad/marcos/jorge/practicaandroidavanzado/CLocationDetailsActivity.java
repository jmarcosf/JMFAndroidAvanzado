/**************************************************************/
/*                                                            */ 
/* CLocationDetailsActivity.java                               */ 
/* (c)2014 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CLocationDetailsActivity Class                 */ 
/*              Práctica asignatura Android Avanzado          */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: January 2014                                  */ 
/*                                                            */ 
/**************************************************************/
package com.utad.marcos.jorge.practicaandroidavanzado;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CLocationDetailsActivity Class                             */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CLocationDetailsActivity extends CBaseActivity
{
public static final String IDS_LATITUDE_PARAM     = "LatitudeParam";
public static final String IDS_LONGITUDE_PARAM    = "LongitudeParam";

private GoogleMap   m_Map     = null;

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Activity Override Methods                             */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */ 
     /* CLocationDetailsActivity.onCreate()                   */ 
     /*                                                       */ 
     /*********************************************************/
     @Override
     protected void onCreate( Bundle savedInstanceState )
     {
          super.onCreate( savedInstanceState );
          setContentView( R.layout.layout_activity_location_details );
          
          Intent intent = this.getIntent();
          
          double Latitude = intent.getDoubleExtra( IDS_LATITUDE_PARAM, 0.0 );
          double Longitude = intent.getDoubleExtra( IDS_LONGITUDE_PARAM, 0.0 );
          LatLng Position = new LatLng( Latitude, Longitude );

          m_Map = ( (SupportMapFragment)getSupportFragmentManager().findFragmentById( R.id.IDC_MAP_LOCATIONMAP ) ).getMap();
          m_Map.addMarker( new MarkerOptions().position( Position ).title( "Marker" ) );
          m_Map.moveCamera( CameraUpdateFactory.newLatLngZoom( Position, 10) );
     }
}
