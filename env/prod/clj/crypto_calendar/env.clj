(ns crypto-calendar.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[crypto-calendar started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[crypto-calendar has shut down successfully]=-"))
   :middleware identity})
