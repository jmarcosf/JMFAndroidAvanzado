PracticaAndroidAvanzado
-----------------------
**Pr�ctica asignatura Android Avanzado, Master Programaci�n de Apps, U-Tad.**

La pr�ctica consiste en realizar una aplicaci�n que tome una fotograf�a y la publique en el muro de Facebook con informaci�n de localizaci�n.

1) *Dise�o general de la interfaz de usuario con Actividades y Fragmentos, utilizando correctamente las vistas XML y los men�s*.
>Actividades:
>>CMainActivity.java              Activiada principal de la aplicaci�n.
>>CPictureDetailsActivity.java:   Detalles de la fotograf�a.
>>CLocationsDetailsActivity.java: Detalles del mapa de locaclizaci�n donde se tom� la fotograf�a.
>>CFacebookActivity.java:         Actividad desde donde se env�a la fotograf�a a Facebook.
>>CWidgetProvider.java            Widget Provider.
>>CMyUploadPhotoRequest.java:     Clase para enviar imagen y datos a Facebook en el mismo Request.
>>CShakeDetector.java:            Listener del sensor que detecta la sacudida del dispositivo.

>Men�s:
>>Existen botones de men� para tomar una fotograf�a, para desecharla y para compartirkla en Facebook.

2) *Uso de la c�mara para realizar fotograf�as.*
>Cuando se pulsa sobre el recuadro de la fotograf�a o en icono del men� se inicia la c�mara con la que se puede tomar la fotograf�a.
>Una vez tomada la fotograf�a, si se pulsa sobre ella se inicia una actividad con la fotograf�a en toda la pantalla.

3) *Uso de la localizaci�n*.
>La parte inferior de la pantalla principal muestra un mapa con la locacilaci�n del dispositivo. Se actualizada cada 10 segundo y por cada 10 metros.
>Este mapa no responde a gestos pero si se pulsa sobre el se inicia una actividad con el mapa en toda la pantalla que s�.

4) *Uso de geocoding*.
>En la parte superior del mapa indica constantemente informaci�n sobre la localizaci�n actual.
>Informaci�n: Latitud y longitud y la localizaci�n exacta del dispositivo.

5) *Integraci�n con Facebook*.
>Una vez tomada la fotograf�a se habilita un icono que al pulsar sobre el se inicia la activiada que env�a la informaci�n a Facebook.
>Tambi�n puede iniciarse al pulsar la opci�n de men� "Compartir".
>Se env�a la fotograf�a y la informaci�n de localizaci�n, geocoding, y punto cardinal (grados y texto).
>>NOTA: No he visto en el SDK de Facebook la manera de enviar imagen y texto en el mismo Request
>>      por lo que he a�adido una clase derivada que lo haga.

6) *Anal�ticas*.
>Se env�a a Google Analytics un registro cada vez que el usuario entra en una activiadad y cada vez que se pulsa cualquier bot�n y opci�n de men�.

7) *Animaciones*.
>La parte superior derecha muestra una br�jula con la direcci�n en grados y punto cardinal al que est� apuntado en dispositivo.

9) *Perfecto funcionamiento de la app*.
>Salvo inesperados, la aplicaci�n funciona correctamente.

**Extras**:

1) *Widget*.
>La aplicaci�n contiene un Widget que al pulsar sobre el se inicia la actividad principal.

2) *Punto cardinal en grados*.
>Adem� del punto cardinal en grados tambi�n se incluye en texto.

3) *Sacudida del dispositivo*.
>- Al sacudir el dispositivo se descarta la fotograf�a actual.
>- NOTA: en unos dispositivos es necesario que la sacudida sea m�s fuerte que en otros. �?.
