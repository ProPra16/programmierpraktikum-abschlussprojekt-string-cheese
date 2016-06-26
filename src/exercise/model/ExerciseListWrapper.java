package exercise.model;

/**
 * Created by Palusch on 26.06.2016.
 */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "exercises")

public class ExerciseListWrapper {

        private List exercises;

        @XmlElement(name = "exercise")
        public List getExercises() {
            return exercises;
        }

        public void setExercises(List exercises) {
            this.exercises = exercises;
        }
    }

