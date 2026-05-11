const friendDetails = {
  ali: {
    title: "Velvet confidence",
    note: "Walks in calm, leaves the frame looking expensive.",
    move: "The crossed-arm entrance",
  },
  bassit: {
    title: "Suit mode",
    note: "Looks like he came prepared for the ceremony and the board meeting.",
    move: "The jacket adjustment",
  },
  gemy: {
    title: "Mirror legend",
    note: "Quiet smile, sharp glasses, and a phone that knows all the angles.",
    move: "The clean selfie",
  },
  gohar: {
    title: "Big smile energy",
    note: "Turns any photo into a group memory without trying too hard.",
    move: "The pocket pose",
  },
  hamed: {
    title: "Campus spark",
    note: "The graduation-day grin that says the stress is finally over.",
    move: "The close-up victory",
  },
  honda: {
    title: "Double thumbs",
    note: "Braces, glasses, lanyard, and the official seal of good vibes.",
    move: "The two thumbs up",
  },
  jo: {
    title: "Quiet final boss",
    note: "Low volume, high presence, impossible to ignore.",
    move: "The hands-in-pocket stare",
  },
  kemo: {
    title: "Clean classic",
    note: "White shirt, calm smile, and a photo that already feels framed.",
    move: "The formal chill",
  },
  omar: {
    title: "Soft rebel",
    note: "Wavy hair, clear glasses, and the kind of look that belongs on a poster.",
    move: "The sunlight selfie",
  },
  samir: {
    title: "Mastered it",
    note: "The thumbs-up proof that the whole university arc paid off.",
    move: "The victory pose",
  },
  samy: {
    title: "Night balcony",
    note: "A hoodie, a stare, and the calm after a thousand deadlines.",
    move: "The balcony lean",
  },
  shbeeb: {
    title: "Street smile",
    note: "Shows up casually and somehow the photo gets better.",
    move: "The phone-in-hand walk",
  },
  zaki: {
    title: "Bright finish",
    note: "All smile, clean fit, and future plans loading.",
    move: "The one-hand pocket",
  },
};

const friends = [
  "ali",
  "bassit",
  "gemy",
  "gohar",
  "hamed",
  "honda",
  "jo",
  "kemo",
  "omar",
  "samir",
  "samy",
  "shbeeb",
  "zaki",
].map((name) => ({
  name,
  photo: `assets/photos/${name}.jpeg`,
  avatar: `assets/avatars/${name}.png`,
  ...friendDetails[name],
}));

const featuredNames = ["zaki", "omar", "shbeeb"];
const featuredFriends = friends.filter((friend) => featuredNames.includes(friend.name));

const awards = [
  "Main character energy",
  "Best laugh in the room",
  "Most likely to save the group",
  "Professor negotiator",
  "Always photo ready",
  "Certified vibe manager",
  "Most dramatic entrance",
  "Group memory keeper",
  "Future CEO behavior",
  "Most likely to arrive late and still win",
  "Best comeback lines",
  "The calm one under pressure",
  "Most likely to make this day legendary",
];

const stageLines = [
  "The official cast of the day we stopped saying someday and started saying remember when.",
  "Same friends, new chapter, better avatars.",
  "A graduation album that came with mini games, inside jokes, and main-character lighting.",
  "Thirteen names, one group chat, unlimited stories.",
];

const reelItems = [
  ["Opening scene", "Everyone arrives pretending they are not emotional."],
  ["Photo mode", "One picture becomes twenty because somebody blinked."],
  ["Main memory", "The group finally stands together and the day feels real."],
  ["After credits", "Food, stories, promises, and one last selfie."],
];

const trioScenes = [
  {
    label: "Poster scene",
    text: "Zaki brings the bright finish, Omar brings the cinematic calm, and Shbeeb brings the street-smile ending shot.",
  },
  {
    label: "Graduation arc",
    text: "Omar sets the mood, Shbeeb keeps it moving, Zaki lands the final frame.",
  },
  {
    label: "Memory trailer",
    text: "Three different energies, one tiny movie: soft rebel, street smile, bright finish.",
  },
];

const squadMissions = [
  {
    title: "Make the group photo legendary",
    brief: "Draft a director, a hype person, and a closer. The app will turn the picks into a mini graduation crew.",
    slots: ["Director", "Hype", "Closer"],
  },
  {
    title: "Save the after-party plan",
    brief: "Choose who negotiates, who gathers everyone, and who gets the final selfie before people disappear.",
    slots: ["Negotiator", "Caller", "Selfie"],
  },
  {
    title: "Build the memory trailer",
    brief: "Pick the opening shot, the plot twist, and the final scene for the day.",
    slots: ["Opening", "Twist", "Finale"],
  },
];

const bingoItems = [
  "Took a group selfie",
  "Said we finally did it",
  "Borrowed a charger",
  "Someone fixed the pose",
  "Parent photo session",
  "Forgot where we parked",
  "Asked for one more picture",
  "Made a professor laugh",
  "Talked about old exams",
  "Promised to meet soon",
  "Someone disappeared",
  "Free space",
  "Shared a memory",
  "Posted a story",
  "Fixed the gown",
  "Sang too loudly",
  "Made a fake serious face",
  "Found an old friend",
  "Talked about future plans",
  "Ate after photos",
  "Someone said don't cry",
  "Took a blurry photo",
  "Made an inside joke",
  "Called the whole group",
  "One perfect picture",
];

let matchFirst = null;
let matchedNames = new Set();
let guessCurrent = null;
let guessStreak = 0;
let spotlightIndex = 0;
let moveRound = 1;
let moveCurrent = null;
let squadMission = null;
let squadPicks = [];

const $ = (selector) => document.querySelector(selector);

function titleName(name) {
  return name.charAt(0).toUpperCase() + name.slice(1);
}

function shuffle(items) {
  return [...items].sort(() => Math.random() - 0.5);
}

function imageWithAvatarFallback(img, friend) {
  img.src = friend.avatar;
  img.onerror = () => {
    img.onerror = null;
    img.src = friend.photo;
  };
}

function renderStage() {
  $("#stageLine").textContent = shuffle(stageLines)[0];
  renderLineup();
  renderMemoryReel();
  setSpotlight(friends[spotlightIndex]);
}

function setSpotlight(friend) {
  const avatar = $("#spotlightAvatar");
  avatar.alt = `${titleName(friend.name)} spotlight avatar`;
  imageWithAvatarFallback(avatar, friend);
  $("#spotlightName").textContent = friend.name;
  $("#spotlightRole").textContent = friend.title;
}

function spinSpotlight() {
  spotlightIndex = (spotlightIndex + 1 + Math.floor(Math.random() * (friends.length - 1))) % friends.length;
  setSpotlight(friends[spotlightIndex]);
}

function renderLineup() {
  const lineup = $("#lineupStage");
  lineup.innerHTML = "";

  friends.forEach((friend) => {
    const item = document.createElement("button");
    item.type = "button";
    item.className = "lineup-avatar";
    item.title = titleName(friend.name);

    const img = document.createElement("img");
    img.alt = titleName(friend.name);
    imageWithAvatarFallback(img, friend);

    item.append(img);
    item.addEventListener("click", () => {
      spotlightIndex = friends.findIndex((candidate) => candidate.name === friend.name);
      setSpotlight(friend);
    });
    lineup.append(item);
  });
}

function renderMemoryReel() {
  const reel = $("#memoryReel");
  reel.innerHTML = "";

  reelItems.forEach(([label, text]) => {
    const card = document.createElement("article");
    card.className = "reel-card";
    card.innerHTML = `<span>${label}</span><strong>${text}</strong>`;
    reel.append(card);
  });
}

function renderTrio() {
  const hero = $("#trioHero");
  hero.innerHTML = "";

  featuredFriends.forEach((friend) => {
    const card = document.createElement("article");
    card.className = `trio-card trio-${friend.name}`;

    const copy = document.createElement("div");
    copy.className = "trio-copy";
    copy.innerHTML = `<span>${friend.title}</span><strong>${friend.name}</strong><p>${friend.note}</p>`;

    const img = document.createElement("img");
    img.alt = `${titleName(friend.name)} redesigned avatar`;
    imageWithAvatarFallback(img, friend);

    card.append(copy, img);
    hero.append(card);
  });

  renderTrioScenes();
}

function renderTrioScenes() {
  const strip = $("#trioStrip");
  strip.innerHTML = "";

  shuffle(trioScenes).forEach((scene) => {
    const card = document.createElement("article");
    card.className = "trio-scene";
    card.innerHTML = `<span>${scene.label}</span><strong>${scene.text}</strong>`;
    strip.append(card);
  });
}

function burstConfetti() {
  const colors = ["#156f64", "#de6d55", "#c49a38", "#315f9b", "#6c4bb8"];

  Array.from({ length: 48 }).forEach((_, index) => {
    const piece = document.createElement("span");
    piece.className = "confetti-piece";
    piece.style.left = `${Math.random() * 100}vw`;
    piece.style.animationDelay = `${Math.random() * 0.38}s`;
    piece.style.background = colors[index % colors.length];
    document.body.append(piece);
    window.setTimeout(() => piece.remove(), 2300);
  });
}

function setMood(mood) {
  document.body.dataset.mood = mood;
  document.querySelectorAll(".mood-button").forEach((button) => {
    button.classList.toggle("active", button.dataset.mood === mood);
  });
}

function renderGallery(list = friends) {
  const grid = $("#friendGrid");
  grid.innerHTML = "";

  list.forEach((friend) => {
    const card = document.createElement("article");
    card.className = `friend-card ${featuredNames.includes(friend.name) ? "featured-friend" : ""}`;

    const badge = document.createElement("span");
    badge.className = "friend-badge";
    badge.textContent = friend.title;

    const img = document.createElement("img");
    img.alt = `${titleName(friend.name)} avatar`;
    imageWithAvatarFallback(img, friend);

    const meta = document.createElement("div");
    meta.className = "friend-meta";
    meta.innerHTML = `<strong>${friend.name}</strong><span>${friend.move}</span>`;

    const note = document.createElement("p");
    note.className = "friend-note";
    note.textContent = friend.note;

    card.append(badge, img, meta, note);
    grid.append(card);
  });
}

function renderMatch() {
  const board = $("#matchBoard");
  board.innerHTML = "";
  matchFirst = null;
  matchedNames = new Set();
  $("#matchScore").textContent = `0 / ${friends.length}`;

  const cards = friends.flatMap((friend) => [
    { type: "photo", friend },
    { type: "name", friend },
  ]);

  shuffle(cards).forEach((cardData) => {
    const card = document.createElement("button");
    card.type = "button";
    card.className = `match-card hidden-card ${cardData.type === "name" ? "name-card" : ""}`;
    card.dataset.name = cardData.friend.name;
    card.dataset.type = cardData.type;

    if (cardData.type === "photo") {
      const img = document.createElement("img");
      img.alt = titleName(cardData.friend.name);
      imageWithAvatarFallback(img, cardData.friend);
      card.append(img);
    } else {
      card.textContent = cardData.friend.name;
    }

    card.addEventListener("click", () => flipMatchCard(card));
    board.append(card);
  });
}

function flipMatchCard(card) {
  if (card.classList.contains("matched") || !card.classList.contains("hidden-card")) return;
  card.classList.remove("hidden-card");

  if (!matchFirst) {
    matchFirst = card;
    return;
  }

  const isMatch =
    matchFirst.dataset.name === card.dataset.name &&
    matchFirst.dataset.type !== card.dataset.type;

  if (isMatch) {
    matchFirst.classList.add("matched");
    card.classList.add("matched");
    matchedNames.add(card.dataset.name);
    $("#matchScore").textContent = `${matchedNames.size} / ${friends.length}`;
    if (matchedNames.size === friends.length) burstConfetti();
    matchFirst = null;
    return;
  }

  const previous = matchFirst;
  matchFirst = null;
  window.setTimeout(() => {
    previous.classList.add("hidden-card");
    card.classList.add("hidden-card");
  }, 700);
}

function renderGuess() {
  guessCurrent = shuffle(friends)[0];
  $(".guess-photo-wrap").classList.remove("revealed");

  const photo = $("#guessPhoto");
  photo.alt = "Blurred graduation friend";
  photo.src = guessCurrent.photo;

  const options = shuffle([
    guessCurrent,
    ...shuffle(friends.filter((friend) => friend.name !== guessCurrent.name)).slice(0, 3),
  ]);

  const optionsWrap = $("#guessOptions");
  optionsWrap.innerHTML = "";

  options.forEach((friend) => {
    const button = document.createElement("button");
    button.type = "button";
    button.textContent = friend.name;
    button.addEventListener("click", () => chooseGuess(button, friend.name));
    optionsWrap.append(button);
  });
}

function chooseGuess(button, name) {
  const buttons = [...$("#guessOptions").querySelectorAll("button")];
  buttons.forEach((item) => {
    item.disabled = true;
    if (item.textContent === guessCurrent.name) item.classList.add("correct");
  });

  $(".guess-photo-wrap").classList.add("revealed");

  if (name === guessCurrent.name) {
    guessStreak += 1;
    if (guessStreak > 0 && guessStreak % 3 === 0) burstConfetti();
  } else {
    button.classList.add("wrong");
    guessStreak = 0;
  }

  $("#guessScore").textContent = guessStreak;
}

function renderMoves() {
  moveCurrent = shuffle(friends)[0];
  $("#moveRound").textContent = `Round ${moveRound}`;
  $("#moveClue").textContent = moveCurrent.move;
  $("#moveFeedback").textContent = "Pick the friend who owns this signature move.";

  const options = shuffle([
    moveCurrent,
    ...shuffle(friends.filter((friend) => friend.name !== moveCurrent.name)).slice(0, 5),
  ]);

  const optionsWrap = $("#moveOptions");
  optionsWrap.innerHTML = "";

  options.forEach((friend) => {
    const button = document.createElement("button");
    button.type = "button";
    button.className = "move-option";
    button.dataset.name = friend.name;

    const img = document.createElement("img");
    img.alt = titleName(friend.name);
    imageWithAvatarFallback(img, friend);

    const label = document.createElement("strong");
    label.textContent = friend.name;

    button.append(img, label);
    button.addEventListener("click", () => chooseMove(button, friend.name));
    optionsWrap.append(button);
  });
}

function chooseMove(button, name) {
  const buttons = [...$("#moveOptions").querySelectorAll("button")];
  buttons.forEach((item) => {
    item.disabled = true;
    item.classList.toggle("correct", item.dataset.name === moveCurrent.name);
  });

  if (name === moveCurrent.name) {
    $("#moveFeedback").textContent = `${titleName(moveCurrent.name)} owns it. Clean guess.`;
    burstConfetti();
  } else {
    button.classList.add("wrong");
    $("#moveFeedback").textContent = `Almost. That move belongs to ${titleName(moveCurrent.name)}.`;
  }
}

function renderSquad(newMission = false) {
  if (!squadMission || newMission) {
    squadMission = shuffle(squadMissions)[0];
    squadPicks = [];
  }

  $("#missionTitle").textContent = squadMission.title;
  $("#missionBrief").textContent = squadMission.brief;
  renderSquadSlots();
  renderSquadPool();
}

function renderSquadSlots() {
  const slots = $("#squadSlots");
  slots.innerHTML = "";

  squadMission.slots.forEach((slot, index) => {
    const pick = squadPicks[index];
    const card = document.createElement("article");
    card.className = `squad-slot ${pick ? "filled" : ""}`;

    if (pick) {
      const img = document.createElement("img");
      img.alt = titleName(pick.name);
      imageWithAvatarFallback(img, pick);
      card.innerHTML = `<span>${slot}</span><strong>${pick.name}</strong>`;
      card.append(img);
    } else {
      card.innerHTML = `<span>${slot}</span><strong>Choose friend</strong>`;
    }

    slots.append(card);
  });
}

function renderSquadPool() {
  const pool = $("#squadPool");
  pool.innerHTML = "";

  friends.forEach((friend) => {
    const selected = squadPicks.some((pick) => pick.name === friend.name);
    const button = document.createElement("button");
    button.type = "button";
    button.className = `squad-option ${selected ? "selected" : ""}`;
    button.disabled = selected || squadPicks.length >= squadMission.slots.length;

    const img = document.createElement("img");
    img.alt = titleName(friend.name);
    imageWithAvatarFallback(img, friend);

    const label = document.createElement("div");
    label.innerHTML = `<strong>${friend.name}</strong><span>${friend.title}</span>`;

    button.append(img, label);
    button.addEventListener("click", () => chooseSquadFriend(friend));
    pool.append(button);
  });
}

function chooseSquadFriend(friend) {
  if (squadPicks.length >= squadMission.slots.length) return;
  squadPicks.push(friend);
  renderSquadSlots();
  renderSquadPool();

  if (squadPicks.length === squadMission.slots.length) {
    const names = squadPicks.map((pick) => titleName(pick.name)).join(", ");
    $("#missionBrief").textContent = `${names}: mission crew locked. This is a poster waiting to happen.`;
    burstConfetti();
  }
}

function renderAwards() {
  const grid = $("#awardGrid");
  grid.innerHTML = "";
  const shuffledFriends = shuffle(friends);

  shuffle(awards).slice(0, friends.length).forEach((award, index) => {
    const friend = shuffledFriends[index];
    const card = document.createElement("article");
    card.className = "award-card";
    card.innerHTML = `<span>${award}</span><strong>${friend.name}</strong><p>${friend.note}</p>`;
    grid.append(card);
  });
}

function renderBingo() {
  const board = $("#bingoBoard");
  board.innerHTML = "";

  bingoItems.forEach((item) => {
    const cell = document.createElement("button");
    cell.type = "button";
    cell.className = "bingo-cell";
    cell.textContent = item;
    cell.addEventListener("click", () => cell.classList.toggle("checked"));
    board.append(cell);
  });
}

function wireTabs() {
  document.querySelectorAll(".tab").forEach((tab) => {
    tab.addEventListener("click", () => {
      document.querySelectorAll(".tab").forEach((item) => item.classList.remove("active"));
      document.querySelectorAll(".view").forEach((view) => view.classList.remove("active"));
      tab.classList.add("active");
      document.getElementById(tab.dataset.view).classList.add("active");
      if (tab.dataset.view === "stage") spinSpotlight();
    });
  });
}

$("#spinSpotlight").addEventListener("click", spinSpotlight);
$("#confettiButton").addEventListener("click", burstConfetti);
$("#trioSceneButton").addEventListener("click", renderTrioScenes);
$("#shuffleGallery").addEventListener("click", () => renderGallery(shuffle(friends)));
$("#resetMatch").addEventListener("click", renderMatch);
$("#nextGuess").addEventListener("click", renderGuess);
$("#nextMove").addEventListener("click", () => {
  moveRound += 1;
  renderMoves();
});
$("#newMission").addEventListener("click", () => renderSquad(true));
$("#resetSquad").addEventListener("click", () => {
  squadPicks = [];
  renderSquad(false);
});
$("#rerollAwards").addEventListener("click", renderAwards);
$("#resetBingo").addEventListener("click", renderBingo);
document.querySelectorAll(".mood-button").forEach((button) => {
  button.addEventListener("click", () => setMood(button.dataset.mood));
});

wireTabs();
setMood("sunset");
renderStage();
renderTrio();
renderGallery();
renderMatch();
renderGuess();
renderMoves();
renderSquad(true);
renderAwards();
renderBingo();
