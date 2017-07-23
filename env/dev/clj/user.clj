(ns user
  (:require [mount.core :as mount]
            crypto-calendar.core))

(defn start []
  (mount/start-without #'crypto-calendar.core/repl-server))

(defn stop []
  (mount/stop-except #'crypto-calendar.core/repl-server))

(defn restart []
  (stop)
  (start))


