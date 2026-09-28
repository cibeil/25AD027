async function loadDashboard(){
  const r = await fetch('/api/dashboard');
  const d = await r.json();
  const items = [
    ['Total Students', d.totalStudents],
    ['Total Subjects', d.totalSubjects],
    ['Total Sessions', d.totalSessions],
    ['Attendance Records', d.totalAttendanceRecords],
    ['Present', d.presentCount],
    ['Absent', d.absentCount],
    ['Shortage Students', d.shortageStudents],
    ['Eligible Students', d.eligibleStudents]
  ];
  document.getElementById('cards').innerHTML = items.map(x =>
    `<div class="card"><div class="label">${x[0]}</div><div class="value">${x[1]}</div></div>`).join('');
}
async function loadAttendance(){
  const date = document.getElementById('dateFilter').value;
  const status = document.getElementById('statusFilter').value;
  const params = new URLSearchParams();
  if(date) params.set('date', date);
  if(status) params.set('status', status);
  const r = await fetch('/api/attendance/filter?' + params.toString());
  const data = await r.json();
  document.getElementById('attendanceBody').innerHTML = data.map(a =>
    `<tr><td>${a.studentName}</td><td>${a.rollNumber}</td><td>${a.subjectCode}</td><td>${a.sessionDate}</td><td class="${a.status.toLowerCase()}">${a.status}</td><td>${a.remarks || ''}</td></tr>`
  ).join('');
}
loadDashboard();
loadAttendance();
