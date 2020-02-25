(ns vajra.xform.core)
;;name space define core transformation functions

(defn bd-aggregator 
  [keys]
  (fn [v1 v2]
    (merge-with (fn [x y] (.add (bigdec x) (bigdec y )) ) 
                (select-keys v1 keys) (select-keys v2 keys))))


(defn aggregate
  "Aggregates the data of form [x y y] => [{:aggregate {} :details [] }]"
  [data group-config]
  
  data
  
  )



(comment
  (->> 
   ((bd-aggregator [:x]) {:x 10 :y 20} {:x 20 :y 3})
   )
)