public class Ticket {

    String ticketID;
    String description;
    String resolution;
    String dateAdded;
    String dateResolved;
    String tech;

    public Ticket(String ticketID, String description, String resolution, String dateAdded, String dateResolved, String tech) {
        this.ticketID = ticketID;
        this.description = description;
        this.resolution = resolution;
        this.dateAdded = dateAdded;
        this.dateResolved = dateResolved;
        this.tech = tech;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateResolved() {
        return dateResolved;
    }

    public void setDateResolved(String dateResolved) {
        this.dateResolved = dateResolved;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketID='" + ticketID + '\'' +
                ", description='" + description + '\'' +
                ", resolution='" + resolution + '\'' +
                ", dateAdded='" + dateAdded + '\'' +
                ", dateResolved='" + dateResolved + '\'' +
                ", tech='" + tech + '\'' +
                '}';
    }
}
