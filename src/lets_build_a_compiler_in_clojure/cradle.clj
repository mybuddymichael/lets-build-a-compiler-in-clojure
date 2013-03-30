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

(def addops #"[+|-]")

(defn add []
  (emitln "ADD (SP)+,D0"))

(defn subtract []
  (str (emitln "SUB (SP)+,D0")
       (emitln "NEG D0")))

(def mulops #"[*|/]")

(defn multiply []
  (emitln "MULS (SP)+,D0"))

(defn divide []
  (str (emitln "MOVE (SP)+,D1")
       (emitln "DIVS D1,D0")))
