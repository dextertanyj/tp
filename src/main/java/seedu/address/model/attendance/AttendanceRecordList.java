package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import seedu.address.model.attendance.exceptions.AttendanceNotFoundException;
import seedu.address.model.attendance.exceptions.InvalidWeekException;
import seedu.address.model.student.Student;

/**
 * Represents the attendance records of a {@code Lesson}.
 * Contains a list of all attendance records.
 * Guarantees: immutable.
 */
public class AttendanceRecordList {

    private final List<AttendanceRecord> recordList;

    /**
     * Constructor method.
     * Each element in the list is initialized to an empty AttendanceRecord.
     */
    public AttendanceRecordList(int numberOfOccurrences) {
        this.recordList = Collections.nCopies(numberOfOccurrences, new AttendanceRecord());
    }

    /**
     * Overloaded constructor method.
     * Requires recordList to be non null. Also converts recordList to fixed size.
     */
    public AttendanceRecordList(List<AttendanceRecord> recordList) {
        requireNonNull(recordList);

        int size = recordList.size();
        this.recordList = Arrays.asList(recordList.toArray(new AttendanceRecord[size]));
    }

    /**
     * Returns true if week number is less than the total number of occurrences.
     */
    private boolean isWeekContained(Week week) {
        return week.getZeroBasedWeekIndex() < recordList.size();
    }

    public List<AttendanceRecord> getAttendanceRecordList() {
        return Collections.unmodifiableList(recordList);
    }

    /**
     * Returns the {@code Attendance} of a {@code Student} in a particular {@code Week}.
     */
    public Attendance getAttendance(Student student, Week week)
            throws InvalidWeekException, AttendanceNotFoundException {
        requireNonNull(student);

        if (!isWeekContained(week)) {
            throw new InvalidWeekException();
        }

        UUID studentUuid = student.getUuid();
        return recordList.get(week.getZeroBasedWeekIndex()).getAttendance(studentUuid);
    }

    /**
     * Returns the {@code AttendanceRecord} of a particular {@code Week}.
     */
    public AttendanceRecord getAttendanceRecord(Week week) throws InvalidWeekException {
        if (!isWeekContained(week)) {
            throw new InvalidWeekException();
        }

        return recordList.get(week.getZeroBasedWeekIndex());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AttendanceRecordList // instanceof handles nulls
                && ((AttendanceRecordList) other).recordList.equals(recordList));
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordList);
    }
}