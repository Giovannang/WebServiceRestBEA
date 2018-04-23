package Servlet.Budget.ChartData;

import java.util.List;

public class DataTable {
    private List<Column> cols; // list of columns
    private List<Row> rows; // list of rows

    public DataTable(List<Column> cols, List<Row> rows) {
        this.cols = cols;
        this.rows = rows;
    }

    public List<Column> getCols() {
        return cols;
    }

    public void setCols(List<Column> cols) {
        this.cols = cols;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public static class Column {
        private String type; // type of column
        private String label; // label of column


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Column(String type, String label) {
            this.type = type;
            this.label = label;
        }
    }



    public static class Row {
        private List<Cell> c; // list of cells

        public Row(List<Cell> c) {
            this.c = c;
        }

        public List<Cell> getC() {
            return c;
        }

        public void setC(List<Cell> c) {
            this.c = c;
        }


        public static class Cell {
            private Object v; // value of cell

            public Cell(Object v) {
                this.v = v;

            }

            public Object getV() {
                return v;
            }

            public void setV(Object v) {
                this.v = v;
            }

        }
    }
}