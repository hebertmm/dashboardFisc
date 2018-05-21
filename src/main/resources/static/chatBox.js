var app = angular.module('chatBox', ['luegg.directives']);
        app.controller('boxCtrl', function($scope, $http, $interval) {
            $scope.messages = []
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