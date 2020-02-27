(ns vajra.xform.core)
;;name space define core transformation functions

(defn bd-aggregator 
  [keys]
  (fn [v1 v2]
    (merge-with (fn [x y] (.add (bigdec x) (bigdec y )) ) 
                (select-keys v1 keys) (select-keys v2 keys))))


(defn aggregate
  "Aggregates the data of form [x y y] => [{:aggregate {} :details [] }]"
  [data group-fn keys]
  (let [gf (fn[v1, v2]
             (merge-with (fn [x y] (.add (bigdec x) (bigdec y)))
                    (select-keys v1 keys) (select-keys v2 keys)))]
    (->> data
         (group-by group-fn)
         (map (fn [[x y]] {:key x 
                           :details (sort-by :y y)
                           :aggregate (reduce gf {} y)})))))



(comment
  
  ((->>
    ((bd-aggregator [:x]) {:x 10 :y 20} {:x 20 :y 3}))

  (def data
    [{:x 1 :y 3}
    {:x 3 :y 56}
    {:x 5 :y 3}
    {:x 1 :y 3}
    {:x 3 :y 3}])
   
(map :aggregate 
     (aggregate data :x [:y]

  
  

   (time  (group-by :x data))

   
   (reduce #(merge-with (fn [x y] (.add (bigdec x) (bigdec y)))) [{:x 1} {:x 1} {:x 1 :y 3}])

   (group-by :x data)

   (count (map (fn [[x y]] {:key x :details (sort y)})  (group-by :x data)))


   (def result {1 [{:x 1, :y 3} {:x 1, :y 3}], 3 [{:x 3, :y 56} {:x 3, :y 3}], 5 [{:x 5, :y 3}]})

   (map (fn [[x y]] {:key x :details (sort-by :y y)}) result)

   (map (fn [[_ y]] y) result)

   (map sort '([{:x 1, :y 3} {:x 1, :y 3}] [{:x 3, :y 56} {:x 3, :y 3}] [{:x 5, :y 3}]))

   (sort- (fn [x1, x2] (< (:y x1) (:y x2))) [{:x 1, :y 6} {:x 1, :y 3}])

   ()

   (time (->> data
              (group-by :x)))



   (defn group-by-with-transducer [key-f xf data]
     (let [ff (memoize (fn [k] (xf conj)))
           r  (reduce (fn [m d]
                        (let [k (key-f d)
                              v (get m k [])]
                          (if (reduced? v)
                            m
                            (assoc m k ((ff k) v d)))))
                      {}
                      data)]
       (into {}
             (map (fn [[k v]] [k ((ff k) (unreduced v))]))
             r)))


   (def xf (comp ()))

   (time (group-by-with-transducer
          :x
          (sort >) data))


   (reduce (fn [m k] k) {} data)

   (assoc {} :x 6)


   (reduce (fn [m d]
             (let [k (:x d)
                   v (get m k [])]
               (if (reduced? v)
                 m
                 (assoc m k ((ff k) v d)))))
           {}
           data)))