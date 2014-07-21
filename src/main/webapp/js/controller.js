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
function CtrlEbooks($scope, $http, focus) {
   $scope.debug = false;
   $scope.base_uri = 'service/ebook/';
   focus('focusMe');

   $http.get($scope.base_uri).success(function (data) {
      $scope.folder = data;
   });

   /**
    * Handles browsing folder.
    * @param category
    */
   $scope.showFolder = function (category) {
      /**
       * Create a path based on the category and the folder.path.
       * @returns {string}
       */
      function createPath() {
         var pth;
         if (category == "..") {
            pth = $scope.folder.path.split("+");
            pth.pop();
            return $scope.base_uri + pth.join("+");
         } else if (category == "/") {
            return $scope.base_uri;
         }
         return $scope.base_uri + $scope.folder.path + "+" + category;
      }

      if (!category) {
         $scope.folder = []
      } else {
         $http.get(createPath()).success(function (data) {
            $scope.folder = data;
            $scope.clearFilter();
         });

      }
   };

   $scope.toggleDebug = function () {
      $scope.debug = !$scope.debug;
   };

   $scope.clearFilter = function () {
      $scope.nameText = '';
      focus('focusMe');
   };

}