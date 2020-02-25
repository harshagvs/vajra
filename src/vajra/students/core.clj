(ns vajra.students.core
  (:require [vajra.students.query :as query]
   [vajra.xform.core :as xform]))


(def sort-configuration 
  {:default [{:name :asc} {:height :desc}]})


(def group-configuration
  {:default (xform/bd-aggregator [:height])
   :strange [:class]})


(defn get-data
 "gets raw data for a given query"
  [param]
  (->> query/get-all
       xform/aggregate group-configuration
       sort sort-configuration))

