(ns vajra.api
  (:require [reitit.ring :as ring]
            [vajra.students.core :as students]))



(defn handler [p]
  {:status 200, :body (students/get-data p)})

(defn wrap [handler id]
  (fn [request]
    (update (handler request) :wrap (fnil conj '()) id)))


(def app
  (ring/ring-handler
   (ring/router
    ["/students" {:middleware [[wrap :api]]}
     ["/all" {:post handler}]])))