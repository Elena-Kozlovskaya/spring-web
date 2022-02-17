angular.module('market-front').controller('recommendationController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555/rec/';

    $scope.loadRecommendations = function () {
        $http({
            url: contextPath + 'api/v1/rec',
            method: 'GET',
        }).then(function (response) {
                $scope.Recommendations = response.data;
            });
    }

    $scope.createRecommendationByOrders = function () {
        console.log('start method createRecommendation()')
        $http({
            url: contextPath + 'api/v1/rec',
            method: 'POST',
            data: $scope.recommendationOrderDetails
        }).then(function (response) {
            $scope.loadRecommendations();
            $scope.recommendationOrderDetails = null
        });
    };

    $scope.createRecommendationByCarts = function () {
        console.log('start method createRecommendationByCarts()')
        $http({
            url: contextPath + 'api/v1/rec',
            method: 'POST',
            data: $scope.recommendationDetails
        }).then(function (response) {
            $scope.loadRecommendations();
            $scope.recommendationDetails = null
        });
    };

});