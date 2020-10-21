package tutorspet.logic.util;

import static tutorspet.commons.util.CollectionUtil.requireAllNonNull;
import static tutorspet.logic.util.AttendanceRecordListUtil.addAttendanceToAttendanceRecordList;
import static tutorspet.logic.util.AttendanceRecordListUtil.editAttendanceInAttendanceRecordList;
import static tutorspet.logic.util.AttendanceRecordListUtil.getAttendanceFromAttendanceRecordList;
import static tutorspet.logic.util.AttendanceRecordListUtil.getAttendances;
import static tutorspet.logic.util.AttendanceRecordListUtil.removeAllStudentsFromAttendanceRecordList;
import static tutorspet.logic.util.AttendanceRecordListUtil.removeAttendanceFromAttendanceRecordList;
import static tutorspet.logic.util.AttendanceRecordListUtil.removeStudentFromAttendanceRecordList;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import tutorspet.logic.commands.exceptions.CommandException;
import tutorspet.model.attendance.Attendance;
import tutorspet.model.attendance.AttendanceRecordList;
import tutorspet.model.attendance.Week;
import tutorspet.model.lesson.Day;
import tutorspet.model.lesson.Lesson;
import tutorspet.model.lesson.NumberOfOccurrences;
import tutorspet.model.lesson.Venue;
import tutorspet.model.student.Student;

/**
 * Contains utility methods for modifying {@code Attendance}s in {@code Lesson}s.
 */
public class LessonUtil {

    /**
     * Returns a {@code Lesson} where the {@code studentToRemove} has been removed.
     */
    public static Lesson deleteStudentFromLesson(Lesson targetLesson, Student studentToRemove) {
        requireAllNonNull(targetLesson, studentToRemove);

        AttendanceRecordList targetAttendanceRecordList = targetLesson.getAttendanceRecordList();

        AttendanceRecordList updatedAttendanceRecordList = removeStudentFromAttendanceRecordList(
                targetAttendanceRecordList, studentToRemove);

        LocalTime startTime = targetLesson.getStartTime();
        LocalTime endTime = targetLesson.getEndTime();
        Day day = targetLesson.getDay();
        NumberOfOccurrences numberOfOccurrences = targetLesson.getNumberOfOccurrences();
        Venue venue = targetLesson.getVenue();

        return new Lesson(startTime, endTime, day, numberOfOccurrences, venue, updatedAttendanceRecordList);
    }

    /**
     * Returns a {@code Lesson} where all {@code Student}s has been removed.
     */
    public static Lesson deleteAllStudentsFromLesson(Lesson targetLesson) {
        requireAllNonNull(targetLesson);

        AttendanceRecordList targetAttendanceRecordList = targetLesson.getAttendanceRecordList();

        AttendanceRecordList updatedAttendanceRecordList = removeAllStudentsFromAttendanceRecordList(
                targetAttendanceRecordList);

        LocalTime startTime = targetLesson.getStartTime();
        LocalTime endTime = targetLesson.getEndTime();
        Day day = targetLesson.getDay();
        NumberOfOccurrences numberOfOccurrences = targetLesson.getNumberOfOccurrences();
        Venue venue = targetLesson.getVenue();

        return new Lesson(startTime, endTime, day, numberOfOccurrences, venue, updatedAttendanceRecordList);
    }

    /**
     * Returns a {@code Lesson} where the {@code attendanceToAdd} has been added to the {@code targetLesson}.
     *
     * @throws CommandException if any of the following violations are found:
     * - the {@code targetWeek} does not exist in the {@code targetLesson}<br/>
     * - there is an existing {@code Attendance} for the {@code targetStudent} in the {@code targetWeek}<br/>
     */
    public static Lesson addAttendanceToLesson(
            Lesson targetLesson, Student targetStudent, Week targetWeek, Attendance attendanceToAdd)
            throws CommandException {
        requireAllNonNull(targetLesson, targetStudent, targetWeek, attendanceToAdd);

        AttendanceRecordList targetAttendanceRecordList = targetLesson.getAttendanceRecordList();

        AttendanceRecordList updatedAttendanceRecordList =
                addAttendanceToAttendanceRecordList(targetAttendanceRecordList,
                        targetStudent, targetWeek, attendanceToAdd);

        // unchanged lesson fields
        LocalTime startTime = targetLesson.getStartTime();
        LocalTime endTime = targetLesson.getEndTime();
        Day day = targetLesson.getDay();
        NumberOfOccurrences numberOfOccurrences = targetLesson.getNumberOfOccurrences();
        Venue venue = targetLesson.getVenue();

        return new Lesson(startTime, endTime, day, numberOfOccurrences, venue, updatedAttendanceRecordList);
    }

    /**
     * Returns a {@code Lesson} where the {@code attendanceToSet} has replaced the existing
     * {@code Attendance} for the {@code targetStudent} in the {@code targetWeek} in the {@code targetLesson}.
     *
     * @throws CommandException if any of the following violations are found:
     * - the {@code targetWeek} does not exist in the {@code targetLesson}<br/>
     * - the {@code Attendance} of the {@code targetStudent} in the {@code targetWeek} does not exist<br/>
     */
    public static Lesson editAttendanceInLesson(
            Lesson targetLesson, Student targetStudent, Week targetWeek, Attendance attendanceToSet)
            throws CommandException {
        requireAllNonNull(targetLesson, targetStudent, targetWeek, attendanceToSet);

        AttendanceRecordList targetAttendanceRecordList = targetLesson.getAttendanceRecordList();

        AttendanceRecordList updatedAttendanceRecordList =
                editAttendanceInAttendanceRecordList(targetAttendanceRecordList,
                        targetStudent, targetWeek, attendanceToSet);

        // unchanged lesson fields
        LocalTime startTime = targetLesson.getStartTime();
        LocalTime endTime = targetLesson.getEndTime();
        Day day = targetLesson.getDay();
        NumberOfOccurrences numberOfOccurrences = targetLesson.getNumberOfOccurrences();
        Venue venue = targetLesson.getVenue();

        return new Lesson(startTime, endTime, day, numberOfOccurrences, venue, updatedAttendanceRecordList);
    }

    /**
     * Returns a {@code Lesson} where the {@code Attendance} for the {@code targetStudent} in the
     * {@code targetWeek} in the {@code targetLesson} has been removed.
     *
     * @throws CommandException if any of the following violations are found:
     * - the {@code targetWeek} does not exist in the {@code targetLesson}<br/>
     * - the {@code Attendance} of the {@code targetStudent} in the {@code targetWeek} does not exist<br/>
     */
    public static Lesson deleteAttendanceFromLesson(
            Lesson targetLesson, Student targetStudent, Week targetWeek) throws CommandException {
        requireAllNonNull(targetLesson, targetStudent, targetWeek);

        AttendanceRecordList targetAttendanceRecordList = targetLesson.getAttendanceRecordList();

        AttendanceRecordList updatedAttendanceRecordList =
                removeAttendanceFromAttendanceRecordList(targetAttendanceRecordList, targetStudent, targetWeek);

        // unchanged lesson fields
        LocalTime startTime = targetLesson.getStartTime();
        LocalTime endTime = targetLesson.getEndTime();
        Day day = targetLesson.getDay();
        NumberOfOccurrences numberOfOccurrences = targetLesson.getNumberOfOccurrences();
        Venue venue = targetLesson.getVenue();

        return new Lesson(startTime, endTime, day, numberOfOccurrences, venue, updatedAttendanceRecordList);
    }

    /**
     * Returns the {@code Attendance} for the {@code targetStudent} in the {@code targetWeek} in the
     * {@code targetLesson}.
     *
     * @throws CommandException if any of the following violations are found:
     * - the {@code targetWeek} does not exist in the {@code targetLesson}<br/>
     * - the {@code Attendance} of the {@code targetStudent} in the {@code targetWeek} does not exist<br/>
     */
    public static Attendance getAttendanceFromLesson(Lesson targetLesson, Student targetStudent, Week targetWeek)
            throws CommandException {
        requireAllNonNull(targetLesson, targetStudent, targetWeek);

        AttendanceRecordList targetAttendanceRecordList = targetLesson.getAttendanceRecordList();

        return getAttendanceFromAttendanceRecordList(targetAttendanceRecordList, targetStudent, targetWeek);
    }

    /**
     * Returns an ordered and unmodifiable {@code List<Optional<Attendance>} summarising the {@code targetStudent}'s
     * attendance in the respective weeks.
     */
    public static List<Optional<Attendance>> getAttendancesFromLesson(Lesson targetLesson, Student targetStudent) {
        requireAllNonNull(targetLesson, targetStudent);

        AttendanceRecordList targetAttendanceRecordList = targetLesson.getAttendanceRecordList();

        return getAttendances(targetAttendanceRecordList, targetStudent);
    }
}
