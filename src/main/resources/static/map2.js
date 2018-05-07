var app = angular.module('myApp', ['ngMap']);
        app.controller('myCtrl', function($scope, $http) {
            $http.get("markersList").then(function(response){
                                        //alert(response.data[2].remoteDevice.lastGeoLocation.coordinates[0]);
                                        $scope.teams = response.data;
            });
            $scope.marker = [-16.2323,-49.622];
        });