package com.example.polygons;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * An activity that displays a Google map with polylines to represent paths or routes,
 * and polygons to represent areas.
 */


public class PolyActivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    private TextView get_place;

    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_PURPLE_ARGB = 0xff81C784;
    private static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_BLUE_ARGB = 0xffF9A825;

    private static final int POLYLINE_STROKE_WIDTH_PX = 20;
    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dot.
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);

    // Create a stroke pattern of a gap followed by a dash.
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    private static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        get_place = (TextView)findViewById(R.id.textView);
        get_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude
                    double latitude = location.getLatitude();
                    //get the logitude
                    double longitude = location.getLongitude();
                    //instantiate the class LatLng
                    LatLng latLng = new LatLng(latitude, longitude);
                    //instantiate Geocoder class
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 20);
                        String str = addressList.get(0).getLocality();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }
        else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    //get the latitude
                    double latitude = location.getLatitude();
                    //get the logitude
                    double longitude = location.getLongitude();
                    //instantiate the class LatLng
                    LatLng latLng = new LatLng(latitude, longitude);
                    //instantiate Geocoder class
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 20);
                        String str = addressList.get(0).getLocality();
                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        }

    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this tutorial, we add polylines and polygons to represent routes and areas on the map.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add polylines to the map.
        // Polylines are useful to show a route or some other connection between points.
        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.306171, -98.169873),
                        new LatLng(26.306031, -98.172438),
                        new LatLng(26.307171, -98.172513),
                        new LatLng(26.307296, -98.170727)));
        // Store a data object with the polyline, used here to indicate an arbitrary type.
        polyline1.setWidth(20);
        polyline1.setColor(COLOR_ORANGE_ARGB);
        polyline1.setTag("Vaquero Trail");
        // Style the polyline.
        stylePolyline(polyline1);

        Polyline polyline2 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.307171, -98.172513),
                        new LatLng(26.307037, -98.176113),
                        new LatLng(26.307011, -98.176773),
                        new LatLng(26.304897, -98.176660)));

        polyline2.setWidth(20);
        polyline2.setColor(COLOR_ORANGE_ARGB);
        polyline2.setTag("Vaquero Trail");
        stylePolyline(polyline2);

        Polyline polyline3 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.307037, -98.176113),
                        new LatLng(26.308582, -98.176129)));

        polyline3.setWidth(20);
        polyline3.setColor(COLOR_ORANGE_ARGB);
        polyline3.setTag("Vaquero Trail");
        stylePolyline(polyline3);

        Polyline polyline4 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.306031, -98.172438),
                        new LatLng(26.305075, -98.172349),
                        new LatLng(26.304897, -98.176660)));

        polyline4.setWidth(20);
        polyline4.setColor(COLOR_ORANGE_ARGB);
        polyline4.setTag("Vaquero Trail");
        stylePolyline(polyline4);

        Polyline polyline5 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.305037, -98.173432),
                        new LatLng(26.304085, -98.173605)));

        polyline5.setWidth(20);
        polyline5.setColor(COLOR_ORANGE_ARGB);
        polyline5.setTag("Vaquero Trail");
        stylePolyline(polyline5);

        Polyline polyline6 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.307264, -98.171041),
                        new LatLng(26.305678, -98.170941),
                        new LatLng(26.305647, -98.171905)));

        polyline6.setWidth(10);
        polyline6.setColor(COLOR_GREEN_ARGB);
        polyline6.setTag("Sidewalk");
        stylePolyline(polyline6);

        Polyline polyline7 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.305678, -98.170941),
                        new LatLng(26.305445, -98.170961),
                        new LatLng(26.305458, -98.171529)));

        polyline7.setWidth(10);
        polyline7.setColor(COLOR_GREEN_ARGB);
        polyline7.setTag("Sidewalk");
        stylePolyline(polyline7);

        Polyline polyline8 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.306031, -98.172438),
                        new LatLng(26.305961, -98.172993),
                        new LatLng(26.306197, -98.173055)));

        polyline8.setWidth(10);
        polyline8.setColor(COLOR_GREEN_ARGB);
        polyline8.setTag("Sidewalk");
        stylePolyline(polyline8);

        Polyline polyline9 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.305961, -98.172993),
                        new LatLng(26.305682, -98.173311),
                        new LatLng(26.305986, -98.173978),
                        new LatLng(26.305640, -98.174161)));

        polyline9.setWidth(10);
        polyline9.setColor(COLOR_GREEN_ARGB);
        polyline9.setTag("Sidewalk");
        stylePolyline(polyline9);

        Polyline polyline10 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.305837, -98.173629),
                        new LatLng(26.305943, -98.173575),
                        new LatLng(26.306131, -98.173999),
                        new LatLng(26.306588, -98.173731)));

        polyline10.setWidth(10);
        polyline10.setColor(COLOR_GREEN_ARGB);
        polyline10.setTag("Sidewalk");
        stylePolyline(polyline10);

        Polyline polyline11 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.306256, -98.173924),
                        new LatLng(26.306554, -98.174643),
                        new LatLng(26.307049, -98.174375)));

        polyline11.setWidth(10);
        polyline11.setColor(COLOR_GREEN_ARGB);
        polyline11.setTag("Sidewalk");
        stylePolyline(polyline11);

        Polyline polyline12 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.306554, -98.174643),
                        new LatLng(26.306838, -98.175303),
                        new LatLng(26.307016, -98.175448)));

        polyline12.setWidth(10);
        polyline12.setColor(COLOR_GREEN_ARGB);
        polyline12.setTag("Sidewalk");
        stylePolyline(polyline12);

        Polyline polyline13 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.307016, -98.175448),
                        new LatLng(26.306795, -98.175614),
                        new LatLng(26.305852, -98.175555),
                        new LatLng(26.305900, -98.174632),
                        new LatLng(26.305689, -98.174144)));

        polyline13.setWidth(10);
        polyline13.setColor(COLOR_GREEN_ARGB);
        polyline13.setTag("Sidewalk");
        stylePolyline(polyline13);

        Polyline polyline14 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.306838, -98.175303),
                        new LatLng(26.305881, -98.175201)));

        polyline14.setWidth(10);
        polyline14.setColor(COLOR_GREEN_ARGB);
        polyline14.setTag("Sidewalk");
        stylePolyline(polyline14);

        Polyline polyline15 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.305852, -98.175555),
                        new LatLng(26.304981, -98.175506)));

        polyline15.setWidth(10);
        polyline15.setColor(COLOR_GREEN_ARGB);
        polyline15.setTag("Sidewalk");
        stylePolyline(polyline15);

        Polyline polyline16 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.305852, -98.175555),
                        new LatLng(26.305796, -98.176658)));

        polyline16.setWidth(10);
        polyline16.setColor(COLOR_GREEN_ARGB);
        polyline16.setTag("Sidewalk");
        stylePolyline(polyline16);

        Polyline polyline17 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(26.306795, -98.175614),
                        new LatLng(26.306753, -98.176696)));

        polyline17.setWidth(10);
        polyline17.setColor(COLOR_GREEN_ARGB);
        polyline17.setTag("Sidewalk");
        stylePolyline(polyline17);


        // Add polygons to indicate areas on the map.
        /*Polygon polygon1 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(-27.457, 153.040),
                        new LatLng(-33.852, 151.211),
                        new LatLng(-37.813, 144.962),
                        new LatLng(-34.928, 138.599)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon1.setTag("alpha");
        // Style the polygon.
        stylePolygon(polygon1);

        Polygon polygon2 = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(-31.673, 128.892),
                        new LatLng(-31.952, 115.857),
                        new LatLng(-17.785, 122.258),
                        new LatLng(-12.4258, 130.7932)));
        polygon2.setTag("beta");
        stylePolygon(polygon2);*/

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.305830, -98.173195), 16));

        // Set listeners for click events.
        googleMap.setOnPolylineClickListener(this);
        //googleMap.setOnPolygonClickListener(this);
    }

    /**
     * Styles the polyline, based on type.
     * @param polyline The polyline object that needs styling.
     */
    private void stylePolyline(Polyline polyline) {
        String type = "";
        // Get the data object stored with the polyline.
        if (polyline.getTag() != null) {
            type = polyline.getTag().toString();
        }

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "A":
                // Use a custom bitmap as the cap at the start of the line.
                polyline.setStartCap(new RoundCap());
                break;
            case "B":
                // Use a round cap at the start of the line.
                polyline.setStartCap(new RoundCap());
                break;
        }
        polyline.setStartCap(new RoundCap());
        polyline.setEndCap(new RoundCap());
        //polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
        //polyline.setColor(COLOR_ORANGE_ARGB);
        polyline.setJointType(JointType.ROUND);
    }

    /**
     * Styles the polygon, based on type.
     * @param polygon The polygon object that needs styling.
     */
    /*private void stylePolygon(Polygon polygon) {
        String type = "";
        // Get the data object stored with the polygon.
        if (polygon.getTag() != null) {
            type = polygon.getTag().toString();
        }

        List<PatternItem> pattern = null;
        int strokeColor = COLOR_BLACK_ARGB;
        int fillColor = COLOR_WHITE_ARGB;

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "alpha":
                // Apply a stroke pattern to render a dashed line, and define colors.
                pattern = PATTERN_POLYGON_ALPHA;
                strokeColor = COLOR_GREEN_ARGB;
                fillColor = COLOR_PURPLE_ARGB;
                break;
            case "beta":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_BETA;
                strokeColor = COLOR_ORANGE_ARGB;
                fillColor = COLOR_BLUE_ARGB;
                break;
        }

        polygon.setStrokePattern(pattern);
        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
        polygon.setStrokeColor(strokeColor);
        polygon.setFillColor(fillColor);
    }*/

    /**
     * Listens for clicks on a polyline.
     * @param polyline The polyline object that the user has clicked.
     */
    @Override
    public void onPolylineClick(Polyline polyline) {
        // Flip from solid stroke to dotted stroke pattern.
        /*if ((polyline.getPattern() == null) || (!polyline.getPattern().contains(DOT))) {
            polyline.setPattern(PATTERN_POLYLINE_DOTTED);
        } else {
            // The default pattern is a solid stroke.
            polyline.setPattern(null);
        }*/

        Toast.makeText(this, "" + polyline.getTag().toString(),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Listens for clicks on a polygon.
     * @param polygon The polygon object that the user has clicked.
     */
    /*@Override
    public void onPolygonClick(Polygon polygon) {
        // Flip the values of the red, green, and blue components of the polygon's color.
        int color = polygon.getStrokeColor() ^ 0x00ffffff;
        polygon.setStrokeColor(color);
        color = polygon.getFillColor() ^ 0x00ffffff;
        polygon.setFillColor(color);

        Toast.makeText(this, "Area type " + polygon.getTag().toString(), Toast.LENGTH_SHORT).show();
    }*/

}
