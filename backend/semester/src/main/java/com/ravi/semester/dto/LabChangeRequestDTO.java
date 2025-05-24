package com.ravi.semester.dto;

import com.ravi.semester.model.LabChangeRequest;

public class LabChangeRequestDTO {
    private Long requestId;
    private String studentId;
    private String studentName;
    private String currentLabName;
    private String desiredLabName;
    private boolean approvedByCurrentFaculty;
    private boolean approvedByDesiredFaculty;
    private String status;
    private String facultyRole; // New field to indicate faculty's role

    public LabChangeRequestDTO(LabChangeRequest request, String facultyId) {
        this.requestId = request.getId();
        this.studentId = request.getStudent().getId();
        this.studentName = request.getStudent().getName();
        this.currentLabName = request.getCurrentLab().getName();
        this.desiredLabName = request.getDesiredLab().getName();
        this.approvedByCurrentFaculty = request.isApprovedByCurrentFaculty();
        this.approvedByDesiredFaculty = request.isApprovedByDesiredFaculty();
        this.status = request.getStatus();

        // Determine faculty role
        if (request.getCurrentLab().getFaculty().getId().equals(facultyId)) {
            this.facultyRole = "CURRENT_FACULTY";
        } else if (request.getDesiredLab().getFaculty().getId().equals(facultyId)) {
            this.facultyRole = "DESIRED_FACULTY";
        } else {
            this.facultyRole = "UNKNOWN"; // This shouldn't happen under normal cases
        }
    }

    public LabChangeRequestDTO(LabChangeRequest labChangeRequest) {
    }

    @Override
    public String toString() {
        return "LabChangeRequestDTO{" +
                "requestId=" + requestId +
                ", studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", currentLabName='" + currentLabName + '\'' +
                ", desiredLabName='" + desiredLabName + '\'' +
                ", approvedByCurrentFaculty=" + approvedByCurrentFaculty +
                ", approvedByDesiredFaculty=" + approvedByDesiredFaculty +
                ", status='" + status + '\'' +
                ", facultyRole='" + facultyRole + '\'' +
                '}';
    }

    public LabChangeRequestDTO() {
    }

    public LabChangeRequestDTO(Long requestId, String studentId, String studentName, String currentLabName, String desiredLabName, boolean approvedByCurrentFaculty, boolean approvedByDesiredFaculty, String status, String facultyRole) {
        this.requestId = requestId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.currentLabName = currentLabName;
        this.desiredLabName = desiredLabName;
        this.approvedByCurrentFaculty = approvedByCurrentFaculty;
        this.approvedByDesiredFaculty = approvedByDesiredFaculty;
        this.status = status;
        this.facultyRole = facultyRole;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCurrentLabName() {
        return currentLabName;
    }

    public void setCurrentLabName(String currentLabName) {
        this.currentLabName = currentLabName;
    }

    public String getDesiredLabName() {
        return desiredLabName;
    }

    public void setDesiredLabName(String desiredLabName) {
        this.desiredLabName = desiredLabName;
    }

    public boolean isApprovedByCurrentFaculty() {
        return approvedByCurrentFaculty;
    }

    public void setApprovedByCurrentFaculty(boolean approvedByCurrentFaculty) {
        this.approvedByCurrentFaculty = approvedByCurrentFaculty;
    }

    public boolean isApprovedByDesiredFaculty() {
        return approvedByDesiredFaculty;
    }

    public void setApprovedByDesiredFaculty(boolean approvedByDesiredFaculty) {
        this.approvedByDesiredFaculty = approvedByDesiredFaculty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFacultyRole() {
        return facultyRole;
    }

    public void setFacultyRole(String facultyRole) {
        this.facultyRole = facultyRole;
    }
}
