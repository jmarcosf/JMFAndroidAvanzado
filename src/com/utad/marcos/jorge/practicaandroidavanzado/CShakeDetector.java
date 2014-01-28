/**************************************************************/
/*                                                            */ 
/* CShakeDetector.java                                        */ 
/* (c)2014 jmarcosf                                           */ 
/*                                                            */ 
/* Description: CShakeDetector Class                          */ 
/*              Práctica asignatura Android Avanzado          */ 
/*              U-Tad - Master Apps                           */ 
/*              www.u-tad.com                                 */ 
/*                                                            */ 
/*        Date: January 2014                                  */ 
/*                                                            */ 
/**************************************************************/
/*                                                            */ 
/* Thanks to: http://jasonmcreynolds.com/?p=388               */ 
/*                                                            */ 
/**************************************************************/
package com.utad.marcos.jorge.practicaandroidavanzado;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.FloatMath;

/**************************************************************/
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/* CShakeDetector Class                                       */ 
/*                                                            */ 
/*                                                            */ 
/*                                                            */ 
/**************************************************************/
public class CShakeDetector implements SensorEventListener
{
/* The gForce that is necessary to register as shake must be greater than 1G */
private static final float    SHAKE_THRESHOLD_GRAVITY       = 2.7F;
private static final int      SHAKE_SLOP_TIME_MS            = 500;
private static final int      SHAKE_COUNT_RESET_TIME_MS     = 3000;

private OnShakeListener       m_Listener = null;
private long                  m_Timestamp;
private int                   m_Count;

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* OnShakeListener Interface Definition                  */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     public interface OnShakeListener
     {
          public void OnShake( int Count );
     }

     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* SensorEventListener Interface Methods                 */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */
     /* CShakeDetector.onAccuracyChanged()                    */
     /*                                                       */
     /*********************************************************/
     @Override
     public void onAccuracyChanged( Sensor arg0, int arg1 )
     {
     }

     /*********************************************************/
     /*                                                       */
     /* CShakeDetector.onSensorChanged()                      */
     /*                                                       */
     /*********************************************************/
     @Override
     public void onSensorChanged( SensorEvent event )
     {
          if( m_Listener != null )
          {
               float x = event.values[ 0 ];
               float y = event.values[ 1 ];
               float z = event.values[ 2 ];

               float gX = x / SensorManager.GRAVITY_EARTH;
               float gY = y / SensorManager.GRAVITY_EARTH;
               float gZ = z / SensorManager.GRAVITY_EARTH;

               // gForce will be close to 1 when there is no movement.
               float gForce = FloatMath.sqrt( gX * gX + gY * gY + gZ * gZ );

               if( gForce > SHAKE_THRESHOLD_GRAVITY )
               {
                    final long Now = System.currentTimeMillis();
                    // ignore shake events too close to each other (500ms)
                    if( m_Timestamp + SHAKE_SLOP_TIME_MS > Now ) return;

                    // reset the shake count after 3 seconds of no shakes
                    if( m_Timestamp + SHAKE_COUNT_RESET_TIME_MS < Now ) m_Count = 0;

                    m_Timestamp = Now;
                    m_Count++;
                    m_Listener.OnShake( m_Count );
               }
          }
     }
     
     /*********************************************************/
     /*                                                       */ 
     /*                                                       */ 
     /* Class Methods                                         */ 
     /*                                                       */ 
     /*                                                       */ 
     /*********************************************************/
     /*                                                       */
     /* CShakeDetector.setOnShakeListener()                   */
     /*                                                       */
     /*********************************************************/
     public void setOnShakeListener( OnShakeListener Listener )
     {
          this.m_Listener = Listener;
     }
}
