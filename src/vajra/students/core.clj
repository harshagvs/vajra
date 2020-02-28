(ns vajra.students.core
  (:require [vajra.students.query :as query]
   [vajra.xform.core :as xform]))

(defn get-data 
  "gets data from datastores for api needs."
  [param]
  (query/get-all))


(defn get-aggregated-data
 "gets aggregated  data processing it for ux needs"
  [param]
  (->> (query/get-all)
       (xform/aggregate :class)))

