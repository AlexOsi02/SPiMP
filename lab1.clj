(defn group-blog-entries-by-tags [blog-entries]
  (reduce (fn [result entry]
            (let [tags (:tags entry)
                  date (:date entry)]
              (reduce (fn [acc tag]
                        (update acc tag conj entry))
                      result
                      tags)))
          {}
          blog-entries))

(defn example-usage []
  (def blog-entries
    [{:title "Record 1", :tags ["Clojure", "functional programming"], :date "2023-10-15"}
     {:title "Record 2", :tags ["Clojure", "web-dev"], :date "2023-10-12"}
     {:title "Record 3", :tags ["Java", "programming"], :date "2023-10-18"}
     {:title "Record 4", :tags ["Clojure", "functional programming"], :date "2023-10-13"}])

  (let [grouped-entries (group-blog-entries-by-tags blog-entries)]
    (doseq [[tag entries] grouped-entries]
      (println (str "Tag: " tag))
      (doseq [entry entries]
        (println (str "  - " (:title entry) " (" (:date entry) ")")))))
  )

(example-usage)

;; Tag: ["Clojure" "functional programming"]
;;  - Record 4 (2023-10-13)
;;  - Record 1 (2023-10-15)
;; Tag: ["Clojure" "web-dev"]
;;  - Record 2 (2023-10-12)
;; Tag: ["Java" "programming"]
;;  - Record 3 (2023-10-18)
