(in-ns 'genetic.impl)

(defn random-chromosome
  "Produces a random sequence of bits"
  [n]
  (take n (repeatedly #(rand-int 2))))

(defn initial-values
  "Returns a vector of maps with all the initial chromosomes and fitnesses."
  [num-chromosomes chromosome-length target-value]
  (repeatedly num-chromosomes #(let [body (apply vector (random-chromosome chromosome-length))
                                     fitness (calculate-fitness (-> body decode-chromosome calculate-expression) target-value)]
                                 (hash-map :body  body
                                           :fitness fitness))))

(defn calculate-expression
  "Calculate the result of our expression in string form.
  Converts infix to valid Clojure code."
  [infix-expresson]
  (try ((from-string infix-expresson))
       (catch Exception e (str "Caught Exception: " (.getMessage e)) Double/POSITIVE_INFINITY)))

(defn calculate-fitness
  "Calculate fitness for our chromosome"
  [value target-value]
  (1.0 / (Math/abs (- value target-value))))

