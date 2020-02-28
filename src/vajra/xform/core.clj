(ns vajra.xform.core)
;;name space define core transformation functions

(defn aggregate
  "creates an aggregate of an array of maps. [{}{}] => {:key somekey :details [] :aggregate somevalue}"
  ([group-fn data]
   (aggregate group-fn (constantly nil) identity data))
  
  ([group-fn agg-fn detail-fn data]
   (->> data
        (group-by group-fn)
        (map (fn [[x y]] {:key x
                          :details (detail-fn y)
                          :aggregate (agg-fn y)}))
        (into []))))


(defn merge-add-with-keys
  "returns a function that merges maps and adds the values of given keys. the resulting function
   willbe used in merge operation on sequence of maps"
  [keys]
  (partial reduce (fn [v1, v2]
                    (merge-with (fn [x y] (.add (bigdec x) (bigdec y)))
                                (select-keys v1 keys) (select-keys v2 keys))) {}))

;;;;;Detail usage examples for future and documentation
(comment
  (def data
    [{:x 1 :y 3, :z 4}
     {:x 3 :y 56}
     {:x 5 :y 3}
     {:x 1 :y 3, :z 3}
     {:x 3 :y 3, :z 34}])

  ((merge-add-with-keys [:y :x]) [{:x 245454542.21 :y -22} {:x 3.2445757 :y 34}])

  (aggregate :x  (merge-add-with-keys [:y :z]) (partial sort-by :x) data)

  ;;[{:key 1, :details ({:x 1, :y 3, :z 4} {:x 1, :y 3, :z 3}), :aggregate {:y 6M, :z 7M}} 
  ;;{:key 3, :details ({:x 3, :y 56} {:x 3, :y 3, :z 34}), :aggregate {:y 59M, :z 34}} 
  ;;{:key 5, :details ({:x 5, :y 3}), :aggregate {:y 3}}]

  [[]]

  (def level-1 (aggregate :x  (merge-add-with-keys [:y :z]) (partial sort-by :x) data))
  (def level-2 (aggregate #(get-in %1 [:aggregate :y])  (merge-add-with-keys [:y :z]) (partial sort-by :x) level-1))
  (def level-3 (aggregate #(get-in %1 [:aggregate :z])  (merge-add-with-keys [:y :z]) (partial sort-by :x) level-2))

  (clojure.pprint/pprint level-3)
  [{:key 6M, :details ({:key 1, :details ({:x 1, :y 3, :z 4} {:x 1, :y 3, :z 3}), :aggregate {:y 6M, :z 7M}}), :aggregate {}} {:key 59M, :details ({:key 3, :details ({:x 3, :y 56} {:x 3, :y 3, :z 34}), :aggregate {:y 59M, :z 34}}), :aggregate {}} {:key 3, :details ({:key 5, :details ({:x 5, :y 3}), :aggregate {:y 3}}), :aggregate {}}])