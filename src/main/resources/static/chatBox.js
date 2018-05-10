var app = angular.module('chatBox', []);
        app.controller('boxCtrl', function($scope, $http, $interval) {

            $http.get("messageList").then(function(response){
                        $scope.messages = response.data;
            });

              $interval(function(){
                  $http.get("messageList").then(function(response){$scope.messages = response.data;});
              },10000);
              $scope.isReceived = function(message){
                if(message.type === "received")
                    return true;
                else
                    return false;
              }
        });