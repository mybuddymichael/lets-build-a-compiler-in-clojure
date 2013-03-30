(ns lets-build-a-compiler-in-clojure.cradle)

(defn expected [s]
  (throw (Exception. (str s " expected"))))

(defn is-alpha? [c]
  (re-seq #"[A-Za-z]" (str c)))

(defn is-digit? [c]
  (re-seq #"[0-9]" (str c)))

(defn get-name [c]
  (if (is-alpha? c)
    c
    (expected "Name")))

(defn get-number [c]
  (if (is-digit? c)
    c
    (expected "Integer")))

(defn emitln [s]
  (str \tab s \newline))

(defn add [c]
  (str (term c)
       (emitln "ADD D1,D0")))

(defn subtract [c]
  (str (term c)
       (emitln "SUB D1,D0")
       (emitln "NEG D0")))

(defn term [s]
  (emitln (str "MOVE #" (get-number s) ",D0")))
