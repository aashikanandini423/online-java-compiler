<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Code Learner</title>
</head>
<link media="all" type="text/css" href="css/home.css" rel="stylesheet">
<jsp:include page="./includes/header.jsp" flush="true" />
<body>
    <div class="container">
    <h2 id="main-title">Code Learner</h2>
    <button type="button" id="logout" class="btn btn-primary login">Logout</button>

    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#home">Home</a></li>
        <li><a data-toggle="tab" href="#viewProblems">Programming Problems</a></li>
        <li><a data-toggle="tab" href="#createProblem">Create Programming Problem</a></li>
    </ul>

    <div class="tab-content">
        <div id="home" class="tab-pane fade in active">
            <h3>HOME</h3>
            <p>Welcome to  Code Learner, <b>${userId}</b>!</p>
        </div>
        <div id="viewProblems" class="tab-pane fade">
            <h3 style="text-align: center">Programming Problems</h3>

            <c:if test="${role == 'Instructor'}">
                <p style="text-align: center">View Student submissions for programming problems.</p>
            </c:if>
            <c:if test="${role == 'Student'}">
                <p style="text-align: center">Solve programming problems assigned to you by your Instructor.</p>
            </c:if>
            <div class="another-main-container">
                <div class="problems-sidebar">
                    <table id="problemsTable">
                    </table>
                </div>

                <div class="problem-worksheet hidden">
                </div>
            </div>
        </div>
        <div id="createProblem" class="tab-pane fade">
            <div class="main-container">
                <div class="problem-form-sheet">
                    <form id="problem-form">
                        <div class="control-group">
                            <h3 class="section-label problem-formulater">Problem Summary</h3>
                        </div>
                        <div class="control-group">
                            <label class="section-label" for="problemTitle">Provide a problem title</label>
                            <input id="problemTitle" type="text" name="problemTitle" maxlength="25" class="section-input" placeholder="Title of the problem"/>
                        </div>
                        <div class="control-group">
                            <label class="section-label" for="problemDescription">Provide a problem description</label>
                            <textarea id="problemDescription" name="problemDescription" maxlength="250" class="section-textarea" placeholder="Describe how the problem should be solved"></textarea>
                        </div>
                        <div class="control-group">
                            <label class="section-label" for="classname">Provide a Java Class name</label>
                            <input type="text" name="className" id="className" class="section-input" placeholder="Java class name"/>
                            <p class="problem-class-name hidden">None</p>
                            <button type="button" class="btn hidden btn-primary" id="code-edit-class">Edit Class name</button>
                        </div>
                        <div class="control-group">
                            <div class="problem-container hidden" name="problemFormulater">
                                <label class="section-label problem-formulater" for="problemFormulater">Problem worksheet</label>
                                <textarea id="problemCodeStart" class="section-textarea" placeholder="Import your libraries here"></textarea>
                                <div class="problem-class-name-start"></div>
                                <textarea spellcheck="false" id="problemCodeEnd" class="section-textarea" placeholder="Start coding here">

                                </textarea>
                                <div class="problem-class-name-end"></div>
                            </div>
                        </div>
                        <div class="control-group">
                            <div class="problem-control-panel hidden">
                                <button type="button" class="btn btn-default code-execute" data-toggle="modal" data-target="#outputModal">Compile & Run</button>
                                <button type="button" class="btn btn-primary code-submit" data-toggle="modal" data-target="#successModal">Create Question</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
         </div>
    </div>
    </div>
    <div id="outputModal" class="modal hide">
        <div class="close-modal" data-dismiss="modal" aria-label="Close"></div>
        <div class="output-space"></div>
    </div>
    <div id="successModal" class="modal hide">
        <div class="success-space">
            <p class="success-message">Your problem has been successfully published.</p>
            <div class="problem-control-panel">
                <button id="view-probs" type="button" class="btn btn-default">View Problems</button>
                <button id="create-prob" type="button" class="btn btn-primary">Create New Problem</button>
            </div>
        </div>
    </div>
    <div id="feedbackModal" class="modal hide">
        <div class="feedback-space">
            <div class="control-group">
                <label class="section-label" for="feedback" style="text-align: center">Feedback</label>
                <textarea id="feedback" class="section-textarea" placeholder="Provide your feedback"></textarea>
                <div class="hover-star">
                    <input name="star1" type="radio" class="star"/>
                    <input name="star1" type="radio" class="star"/>
                    <input name="star1" type="radio" class="star"/>
                    <input name="star1" type="radio" class="star"/>
                    <input name="star1" type="radio" class="star"/>
                </div>
            </div>
            <div class="problem-control-panel">
                <button id="save-feedback" type="button" class="btn btn-primary" data-dismiss="modal" aria-label="Close">Save feedback</button>
            </div>
        </div>
    </div>
    <span id="loggedInUser" class="hidden"></span>
    <span id="userId" class="hidden">${userId}</span>
    <span id="userRole" class="hidden">${role}</span>

</body>

<!-- Handlebars template -->
<script type="text/x-handlebars-template" id="output-template">
    {{#each this}}
        <p class="output-line">{{this}}<p><br>
    {{/each}}
</script>

<script type="text/x-handlebars-template" id="problem-details-template">
    {{#if this}}
        <div class="control-group">
        <p class="display-problem-title">{{problem.problemTitle}} <span class="status-label">(Status : </span><span class="status {{#if problem.isDisabled}}status-answered{{else}}status-unanswered{{/if}}">{{#if problem.isDisabled}}ANSWERED{{else}}UNANSWERED{{/if}}</span><span class="status-label">)</span></p>
        </div>
        <div class="control-group">
            <p class="display-problem-description">{{problem.problemDesc}}</p>
        </div>

        <div class="problem">
                <textarea onkeydown="if(event.keyCode===9){var v=this.value,s=this.selectionStart,e=this.selectionEnd;this.value=v.substring(0, s)+'\t'+v.substring(e);this.selectionStart=this.selectionEnd=s+1;return false;}" spellcheck="false" id="problemCode" class="section-textarea" {{#if problem.isDisabled}}disabled{{/if}}>
                {{#each problem.codeLines}}{{this}}{{/each}}
                </textarea>
        </div>

        <div class="problem-control-panel">
            <button type="button" class="btn btn-default code-execute"  data-toggle="modal" data-target="#outputModal" style="margin-left: 70px;">Compile & Run</button>
            <button type="button" class="btn btn-primary code-submit" {{#if problem.isDisabled}}disabled{{/if}}>Submit Answer</button>
            {{#if problem.isFeedbackAvailable}}<a class="view-feedback" data-toggle="modal" data-target="#feedbackModal" data-answer-id="{{this.problemId}}" style="margin-right: 90px;">View feedback>></a>{{/if}}
        </div>
    {{/if}}
</script>

<script type="text/x-handlebars-template" id="admin-problem-details-template">
    {{#if this}}
        <div class="control-group">
        <p class="display-problem-title">{{problem.problemTitle}} <span class="responses">(Answers submitted: {{answers.length}})</span></p>
        </div>
        <div class="control-group">
            <p class="display-problem-description">{{problem.problemDesc}}</p>
        </div>

        <div class="problem">
                <textarea spellcheck="false" id="problemCode" class="section-textarea" disabled>
                {{#each problem.codeLines}}{{this}}{{/each}}
                </textarea>
        </div>
        <hr>

        {{#each answers}}
            <div class="control-group">
                <p class="display-problem-answer"><span style="font-weight: bold;">Answer submitted by: </span>{{this.answeredBy}}</p>
            </div>

            <div class="problem">
                <textarea spellcheck="false" id="problemCode" class="section-textarea" disabled>
                {{#each this.codeLines}}{{this}}{{/each}}
                </textarea>
            </div>

            <div class="problem-control-panel">
                <button type="button" class="btn btn-default code-execute"  data-toggle="modal" data-target="#outputModal" style="margin-left: 90px ;">Compile & Run</button>
                <a class="submit-feedback" data-toggle="modal" data-target="#feedbackModal" data-answer-id="{{this.problemId}}">Submit feedback>></a>
            </div>
            <hr>
        {{/each}}
    {{/if}}
</script>

<jsp:include page="./includes/footer.jsp" flush="true" />
<script type="text/javascript" src="js/home.js"></script>
<script type="text/javascript" src="js/urls.js"></script>
</html>