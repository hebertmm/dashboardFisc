      var map;
      var bounds;
      var infoWindow;
      document.addEventListener('DOMContentLoaded', function () {
          if (document.querySelectorAll('#map').length > 0)
          {
            if (document.querySelector('html').lang)
              lang = document.querySelector('html').lang;
            else
              lang = 'en';

            var js_file = document.createElement('script');
            js_file.type = 'text/javascript';
            js_file.src = 'https://maps.googleapis.com/maps/api/js?callback=initMap&key=AIzaSyC9Uc93DkXYoiGlPSymsQ1a2EQj75UVsxo&language=' + lang;
            document.getElementsByTagName('head')[0].appendChild(js_file);
          }
        });
      function initMap() {
        infoWindow = new google.maps.InfoWindow({content: "Teste"});
        bounds = new google.maps.LatLngBounds();
        map = new google.maps.Map(document.getElementById('map'), {
          zoom: 7,
          center: {lat:-16.33, lng:-49.18},
        });

        plotMarkers(-16.6921,-49.2677);
        plotMarkers(-16.6062,-49.3315);
        plotMarkers(-16.3362,-48.9534);
      }

  function plotMarkers(lat, long)
  {
    markers = [];

    var position = new google.maps.LatLng(lat, long);
    var mark = new google.maps.Marker({
          position: position,
          label: "teste",
          map: map,
          animation: google.maps.Animation.DROP,
          icon: {url: "target.png", labelOrigin: {x:0,y:0}}

        })
    bounds.extend(position);
    map.fitBounds(bounds);
    google.maps.event.addListener(mark, 'click', function(){
        infoWindow.open(map, mark);
    });
  }

