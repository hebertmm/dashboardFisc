var app = angular.module('myApp', ['ngMap']);
        app.controller('myCtrl', function($scope, $http, $interval, NgMap) {
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
            $http.get("messageList").then(function(response){
                        $scope.messages = response.data;
            });
            $scope.description = "";
            $scope.status = "";
            $scope.iconTeam = {url: 'group.png', labelOrigin:{x:0,y:0}};

            $scope.showDetail = function(e, item) {
                $scope.description = item.description;
                $scope.status = item.remoteDevice.status;
                this.map.showInfoWindow('foo-iw', 'a'+item.id);
              };

              $scope.hideDetail = function() {
                this.map.hideInfoWindow('foo-iw');
              };

              $scope.getIcon = function(status){
                switch(status){
                    case "NAO_INICIADO":
                        return "groupGray.png";
                        break;
                    case "DESLOCAMENTO":
                        return "groupYellow.png";
                        break;
                    case "AÇÃO":
                        return "groupGreen.png";
                        break;
                    case "ENCERRADA":
                        return "groupBlue.png";
                        break;
                    case "EM_ATENÇÃO":
                        return "groupRed.png";
                        break;
                    default:
                        return "groupGray.png"

                }
              };
              $interval(function(){
                $http.get("markersList").then(function(response){$scope.teams = response.data;});
                $http.get("messageList").then(function(response){$scope.messages = response.data;});
              },10000);
        });