(ns lets-build-a-compiler-in-clojure.cradle)

(defn expected [s]
  (throw (Exception. (str s " expected"))))

(defn match [x y]
  (= x y))

(defn is-alpha? [c]
  (re-seq #"[A-Za-z]" (str c)))

(defn is-digit? [c]
  (re-seq #"[0-9]" (str c)))

(defn get-name [c]
  (if (is-alpha? c)
    c
    (expected "Name")))

(defn get-num [c]
  (if (is-digit? c)
    c
    (expected "Integer")))

(defn emit [s]
  (str \tab s))

(defn emitln [s]
  (str \tab s \newline))

(defn term [s]
  (emitln (str "MOVE #" (get-num s) ",D0")))

(defn add []
  (emitln "ADD D1,D0"))

(defn sub []
  (str (emitln "SUB D1,D0")
       (emitln "NEG D0")))

(defn expression [[x y z]]
  (let [op (cond
             (= y \+) (add)
             (= y \-) (sub)
             :else (expected "Addop"))]
    (str (term x)
         (emitln "MOVE D0,D1")
         (term z)
         op)))
