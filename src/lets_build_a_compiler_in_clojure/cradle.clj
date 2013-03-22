(ns lets-build-a-compiler-in-clojure.cradle)

(defn expected [s]
  (str s " expected"))

(defn match [x y]
  (= x y))

(defn is-alpha? [c]
  (re-seq #"[A-Za-z]" c))

(defn is-digit? [c]
  (re-seq #"[0-9]" c))

(defn get-name [c]
  (if is-alpha? c
    c
    (expected "Name")))

(defn get-num [c]
  (if is-digit? c
    c
    (expected "Integer")))

(defn emit [s]
  (str \tab s))

(defn emitln [s]
  (str \tab s))
