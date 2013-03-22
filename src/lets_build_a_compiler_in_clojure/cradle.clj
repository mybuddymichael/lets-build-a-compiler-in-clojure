(ns lets-build-a-compiler-in-clojure.cradle)

(defn expected [s]
  (throw (Exception. (str s " expected"))))

(defn match [x y]
  (= x y))

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
