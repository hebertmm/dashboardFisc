var app = angular.module('console', []);
app.controller('consoleCtrl', function($scope, $http) {
    $http.get("teamsList").then(function(response){
            $scope.teams = response.data;
    });
    $scope.update = function(id, data) {
        $http.put("updateRemoteDevice/"+id, data).then(function(response){
            alert(response.data);
        })
    }


});