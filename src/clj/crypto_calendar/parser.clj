(ns crypto-calendar.parser
  (:use [hickory.core])
  (:require [clj-http.client :as client]
            [hickory.select :as s]))

(def base-url "https://icotracker.net/")

(def main-page (atom (-> (client/get base-url)
             :body parse as-hickory)))

(defn card-blocks [page]
  (s/select
    (s/class "card-block") page))

(defn currencies-blocks []
  (let [blocks (card-blocks @main-page)
        project_names (flatten
                          (mapv #(-> % :content)
                            (flatten (mapv #(s/select
                              (s/descendant
                                (s/tag :h2)
                                (s/tag :a)) %) blocks))))
        description "Good one!"
        list_data  (map #(assoc {} :name % :description description) project_names)
        left_block (take (/ (count list_data) 2) list_data)
        right_block (drop (/ (count list_data) 2) list_data)]
    {:left left_block
     :right right_block}))
