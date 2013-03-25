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

(defn get-num [c]
  (if (is-digit? c)
    c
    (expected "Integer")))

(defn emitln [s]
  (str \tab s \newline))

(defn term [s]
  (emitln (str "MOVE #" (get-num s) ",D0")))

(defn add []
  (emitln "ADD (SP)+,D0"))

(defn sub []
  (str (emitln "SUB (SP)+,D0")
       (emitln "NEG D0")))

(defn get-op [c]
  (cond
    (= c \+) (add)
    (= c \-) (sub)
    :else (expected "Addop")))

(defn sub-expression [[op t & more]]
  (str (emitln "MOVE D0,-(SP)")
       (term t)
       (get-op op)
       (when (seq more) (sub-expression more))))

(defn expression [[t & more]]
  (str (term t)
       (when (seq more)
         (sub-expression more))))
