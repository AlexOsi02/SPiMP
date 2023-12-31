(ns file-reader.core
  (:require [clojure.java.io :as io]
            [clojure.core.async :as async :refer [chan put! <! go]]))

(defn read-char-channel [file-path]
  (let [char-channel (async/chan)]
    (go
      (with-open [reader (io/reader file-path)]
        (let [buffer (make-array Character/TYPE 1)]
          (loop []
            (when (= 1 (.read reader buffer))
              (async/put! char-channel (char buffer 0))
              (recur))))))
    char-channel)

(defn char-channel-to-string-channel [char-channel]
  (let [string-channel (chan)]
    (go
      (loop []
        (let [char (<! char-channel)]
          (when char
            (go (>! string-channel (str char)))
            (recur)))))))

(defn example-usage []
  (let [char-channel (read-char-channel "./file.txt")
        string-channel (char-channel-to-string-channel char-channel)]
    (go
      (loop []
        (let [str-value (<! string-channel)]
          (when str-value
            (println str-value)
            (recur)))))))

(example-usage)
