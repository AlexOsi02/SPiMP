(defn group-blog-entries-by-tags [blog-entries]
  (let [grouped-blog-entries (group-by :tags blog-entries)]
    (into {} (for [[tag entries] grouped-blog-entries]
               [tag (sort-by :date entries)]))))

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