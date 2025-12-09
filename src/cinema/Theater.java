package cinema;

public class Theater {


        private int id;
        private String name;
        private int numberOfRows;
        private int seatsPerRow;
        private int totalCapacity;

        public Theater(int id, String name, int numberOfRows, int seatsPerRow) {
            this.id = id;
            this.name = name;
            this.numberOfRows = numberOfRows;
            this.seatsPerRow = seatsPerRow;
            this.totalCapacity = numberOfRows * seatsPerRow;
        }

        // Getters and Setters
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getNumberOfRows() { return numberOfRows; }
        public void setNumberOfRows(int numberOfRows) {
            this.numberOfRows = numberOfRows;
            this.totalCapacity = numberOfRows * seatsPerRow;
        }

        public int getSeatsPerRow() { return seatsPerRow; }
        public void setSeatsPerRow(int seatsPerRow) {
            this.seatsPerRow = seatsPerRow;
            this.totalCapacity = numberOfRows * seatsPerRow;
        }

        public int getTotalCapacity() { return totalCapacity; }

        @Override
        public String toString() {
            return "Theater{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", capacity=" + totalCapacity + " seats" +
                    " (" + numberOfRows + "x" + seatsPerRow + ")" +
                    '}';
        }
    }



