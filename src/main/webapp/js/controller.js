/*
 * Copyright (c) 2013 by Ivo Woltring (http://ivonet.nl)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

/**
 * The EBooks Controller.
 *
 * @param $scope the scope for the application
 * @param $http for doing http requests
 */
function CtrlEbooks($scope, $http) {
    $scope.debug = false;

    $http.get('service/ebook/').success(function (data) {
        $scope.folders = data;
    });

    $scope.toggleDebug = function () {
        $scope.debug = !$scope.debug;
    };

    $scope.showFolder = function (category) {
        if (!category) {
            $scope.folders = []
        } else {
            if (category == "..") {
                pth = $scope.folders.path.split("+");
                pth.pop();
                $scope.path = 'service/ebook/' + pth.join("+");
            } else if (category == "/") {
                $scope.path = 'service/ebook/';
            } else {
                $scope.path = 'service/ebook/' + $scope.folders.path + "+" + category;
            }
            $http.get($scope.path).success(function (data) {
                $scope.folders = data;
            });

        }
    };
}