(ns crypto-calendar.routes.home
  (:require [crypto-calendar.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [crypto-calendar.parser :as parser]))

(defn home-page [{:keys [params]}]
  (layout/render
   "home.html"
   (merge {:names (parser/project-names)})))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" request (home-page request)))

