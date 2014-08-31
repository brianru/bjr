(ns brian.models.schema
  (:require [fogus.datalog.bacwn :refer [q build-work-plan run-work-plan]]
            [fogus.datalog.bacwn.macros :refer [<- ?- make-database]]
            [fogus.datalog.bacwn.impl.rules :refer [rules-set]]
            ))

(def db-base
  (make-database
    (relation :book [:isbn :author :title])
    (index :book :isbn)

    (relation :start [:isbn :when])
    (index :start :isbn)

    (relation :finish [:isbn :when])
    (index :finish :isbn)))

; Connect relations (think joins)..
; TODO How do I exclude matches?
;      Success should be attempt with finish.
;      Failure should be attempt without finish.
(def rules
  (rules-set
    (<- (:attempt :author ?a :title ?t :when ?w)
        (:book :isbn ?id :author ?a :title ?t)
        (:start :isbn ?id :when ?w))

    (<- (:success :author ?a :title ?t :when ?w)
        (:book :isbn ?id :author ?a :title ?t)
        (:finish :isbn ?id :when ?w))

    (<- (:failure :author ?a :title ?t :when ?w)
        (:book :isbn ?id :author ?a :title ?t)
        (:start :isbn ?id :when ?w))))

(def wp-1 (build-work-plan rules (?- :attempt :author ?a
                                     :title ?t :when ?q)))
