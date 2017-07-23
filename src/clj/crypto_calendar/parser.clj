(ns crypto-calendar.parser
  (:use [hickory.core])
  (:require [clj-http.client :as client]
            [hickory.select :as s]))

(def base-url "https://icotracker.net/")

(def main-page (atom (-> (client/get base-url)
             :body parse as-hickory)))

(defn card-blocks [page] (s/select
      (s/class "card-block") page))

(defn project-names []
  (flatten
    (mapv #(-> % :content)
      (flatten (mapv #(s/select
                      (s/descendant
                        (s/tag :h2)
                        (s/tag :a)) %) (card-blocks @main-page))))))
