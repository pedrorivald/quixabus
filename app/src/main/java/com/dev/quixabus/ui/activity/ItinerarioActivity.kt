package com.dev.quixabus.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.dev.quixabus.R
import com.dev.quixabus.dao.ItinerarioDao
import com.dev.quixabus.databinding.ActivityItinerarioBinding
import com.dev.quixabus.util.TopBar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions


class ItinerarioActivity : AppCompatActivity(), OnMapReadyCallback {

    private val binding by lazy {
        ActivityItinerarioBinding.inflate(layoutInflater)
    }

    private val dao = ItinerarioDao()

    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val defaultLocation = LatLng(-4.968541343386872, -39.01614599022661)
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TopBar().configura(supportFragmentManager, R.id.activity_itinerario_fragment_top_bar)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        configuraFab()
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map

        getLocationPermission()
        updateStyle()
        updateLocationUI()
        updateMarkers()
        getDeviceLocation()
    }

    private fun updateMarkers() {

        dao.buscarParadas() { paradas ->
            if(paradas != null) {
                paradas.forEach {

                    val icon = getMarkerIconFromDrawable(getParadaTipoDrawable(it.tipo))

                    map.addMarker(
                        MarkerOptions()
                            .title(it.tipo)
                            .position(LatLng(it.latitude, it.longitude))
                            .snippet(it.endereco)
                            .icon(icon)
                    )
                }
            } else {
                Toast.makeText(this, "Não foi possível obter as paradas, tente novamente mais tarde.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getMarkerIconFromDrawable(drawable: Drawable): BitmapDescriptor? {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun updateStyle() {
        map.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style)
        )
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }

            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        updateLocationUI()
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    ), DEFAULT_ZOOM.toFloat()
                                )
                            )
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat())
                        )
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    companion object {
        private val TAG = ItinerarioActivity::class.java.simpleName
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }

    private fun getParadaTipoDrawable(tipo: String): Drawable {
        return when(tipo) {
            "Desembarque" -> ResourcesCompat.getDrawable(resources, R.drawable.marker_bus_desembarque, null)
            "Embarque" -> ResourcesCompat.getDrawable(resources, R.drawable.marker_bus_embarque, null)
            "Campus" -> ResourcesCompat.getDrawable(resources, R.drawable.marker_flag_circle_campus, null)
            "Rodoviária" -> ResourcesCompat.getDrawable(resources, R.drawable.marker_flag_circle_rodoviaria, null)
            else -> ResourcesCompat.getDrawable(resources, R.drawable.marker_bus_desembarque, null)
        }!!
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(this, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menus_action_agenda -> {
                    Toast.makeText(this, "Item do Menu Clicado", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }

        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }

        popup.show()
    }
    private fun vaiParaHorarios() {
        val intent = Intent(this, HorariosActivity::class.java)
        startActivity(intent)
    }

    private fun configuraFab() {
        val fab = binding.activityItinerarioFab
        fab.setOnClickListener {
            vaiParaHorarios()
        }
    }
}