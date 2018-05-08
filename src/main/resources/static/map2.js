var app = angular.module('myApp', ['ngMap']);
        app.controller('myCtrl', function($scope, $http, NgMap) {
            NgMap.getMap().then(function(map) {
                console.log('map', map);
                this.map = map;
              });
            $http.get("markersList").then(function(response){
                                        //alert(response.data[2].remoteDevice.lastGeoLocation.coordinates[0]);
                                        $scope.teams = response.data;
            });
            $http.get("targetsList").then(function(response){
                        $scope.targets = response.data;

            });
            $scope.description = "";
            $scope.showDetail = function(e, item) {
                //alert(item.description);
                $scope.description = item.description;
                this.map.showInfoWindow('foo-iw', item.id);
              };

              $scope.hideDetail = function() {
                this.map.hideInfoWindow('foo-iw');
              };

        });