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

(defn factor [s]
  (emitln (str "MOVE #" (get-number s) ",D0")))

(defn add [c]
  (str (factor c)
       (emitln "ADD (SP)+,D0")))

(defn subtract [c]
  (str (factor c)
       (emitln "SUB (SP)+,D0")
       (emitln "NEG D0")))

(defn multiply [c]
  (str (factor c)
       (emitln "MULS (SP)+,D0")))

(defn divide [c]
  (str (factor c)
       (emitln "MOVE (SP)+,D1")
       (emitln "DIVS D1,D0")))

(defn expression [s]
  (let [sub-expression
        (fn [string [op t & more]]
          (if op
            (recur (str string
                        (emitln "MOVE D0,-(SP)")
                        (cond
                          (= op \+) (add t)
                          (= op \-) (subtract t)
                          :else (expected "Addop")))
                   more)
            string))]
    (str (factor (first s))
         (sub-expression nil (rest s)))))
