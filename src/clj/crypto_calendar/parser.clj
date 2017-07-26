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
        description (flatten
                          (mapv #(-> % :content)
                            (flatten (mapv #(s/select
                              (s/descendant
                                (s/class :cp-prj-descr)
                                (s/tag :div)) %) blocks))))
        launch  (flatten
                          (mapv #(-> % :content)
                            (flatten (mapv #(s/select
                              (s/descendant
                                (s/class :cp-what)
                                s/first-child
                                (s/class :text-black)) %) blocks))))
        base  (flatten
                          (mapv #(-> % :content)
                            (flatten (mapv #(s/select
                              (s/descendant
                                (s/class :cp-what)
                                (s/nth-child 2)
                                (s/class :text-black)) %) blocks))))
        website (flatten
                          (mapv #(-> % :content)
                            (flatten (mapv #(s/select
                              (s/descendant
                                (s/class :cp-who)
                                (s/nth-child 2)
                                (s/tag :a)
                                (s/tag :span)) %) blocks))))
        list_data  (map #(assoc {}
                           :name %1
                           :description %2
                           :launch %3
                           :base %4
                           :website %5) project_names description launch base website)]

    {:items list_data}))
