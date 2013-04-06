(ns lets-build-a-compiler-in-clojure.cradle)

(defmacro debug [x]
  `(let [x# ~x]
     (println "debug:" '~x "=" x#)
     x#))

;; Our syntax in EBNF.
;; factor = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" ;
;; mathop = "+" | "-" | "*" | "/" ;
;; expression = factor | "(" , mathop , expression , { expression } , ")" ;

(defn expected [s]
  (throw (Exception. (str s " expected"))))

(def factor-regex #"^[0-9]$")
(def mathop-regex #"^[\+\-\*\/]$")
