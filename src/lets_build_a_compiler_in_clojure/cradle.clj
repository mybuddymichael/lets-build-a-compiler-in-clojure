(ns lets-build-a-compiler-in-clojure.cradle)

(def look-char (atom nil))

(defn get-char []
  (let [c (read)]
    (reset! look-char c)))

(defn error [s]
  (throw (Exception. (str "Error: " s))))

(defn expected [s]
  (error (str s " Expected")))

(defn match [c]
  (if (= look-char c)
    (get-char)
    (expected c)))

(defn is-alpha? [c]
  (re-seq #"[A-Z]" (clojure.string/upper-case c)))

(defn is-digit? [c]
  (re-seq #"[0-9]" c))

(defn get-name []
  (let [c look-char]
    (when (not (is-alpha? c))
      (expected "Name"))
    (get-char)
    (clojure.string/upper-case c)))

(defn emit [s]
  (print (str \tab s)))

(defn emitln [s]
  (println (str \tab s)))
