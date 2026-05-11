const friendDetails = {
  "3bbas": {
    title: "Garden night",
    note: "Quiet night-shot energy with a clean black jacket and calm presence.",
    move: "The night garden stance",
  },
  amgad: {
    title: "Mirror sharp",
    note: "Clean fit, phone pose, and the kind of calm that looks intentional.",
    move: "The pocket mirror shot",
  },
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
  karim: {
    title: "Night formal",
    note: "Glasses, suit, bright smile, and ceremony energy after dark.",
    move: "The suit smile",
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
    title: "The headline",
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
  shreef: {
    title: "Beard frame",
    note: "A strong close-up look turned into a full-body sticker with edge.",
    move: "The serious selfie stare",
  },
  zaid: {
    title: "AI campus smile",
    note: "Bright smile, simple fit, and campus-day energy.",
    move: "The AI sign smile",
  },
  zaki: {
    title: "Bright finish",
    note: "The new burgundy avatar brings the clean final-frame energy.",
    move: "The burgundy pocket pose",
  },
};

const friendNames = [
  "3bbas",
  "amgad",
  "ali",
  "bassit",
  "gemy",
  "gohar",
  "hamed",
  "honda",
  "jo",
  "karim",
  "kemo",
  "omar",
  "samir",
  "samy",
  "shbeeb",
  "shreef",
  "zaid",
  "zaki",
];

const friends = friendNames.map((name) => ({
  name,
  photo: `assets/photos/${name}.jpeg`,
  avatar: `assets/avatars/${name}.png`,
  ...friendDetails[name],
}));

const featuredNames = ["samir", "zaki", "shreef", "zaid"];

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
  "Final-photo specialist",
  "Best fit check",
  "Most likely to start the chant",
  "Group-chat historian",
  "Ceremony scene stealer",
];

const stageLines = [
  "The official cast of the day we stopped saying someday and started saying remember when.",
  "Same friends, new chapter, better avatars.",
  "A graduation album with games, inside jokes, and main-character lighting.",
  "One group chat, unlimited stories.",
];

const reelItems = [
  ["Opening scene", "Everyone arrives pretending they are not emotional."],
  ["Photo mode", "One picture becomes twenty because somebody blinked."],
  ["Samir spotlight", "The headline energy stays right in the middle."],
  ["After credits", "Food, stories, promises, and one last selfie."],
];

const vibeRounds = [
  {
    title: "Build the final photo crew",
    brief: "Pick three friends who can turn the last picture into the one everyone reposts.",
  },
  {
    title: "Save the after-party mood",
    brief: "Pick three friends who can keep the energy alive after the ceremony ends.",
  },
  {
    title: "Make the campus walk iconic",
    brief: "Pick three friends for the slow walk, the laugh, and the final pose.",
  },
  {
    title: "Turn the group chat into a movie",
    brief: "Pick three friends to carry the intro, plot twist, and final scene.",
  },
];

let matchFirst = null;
let matchedNames = new Set();
let spotlightIndex = friends.findIndex((friend) => friend.name === "samir");
let vibeRound = 1;
let vibeCurrent = null;
let vibePicks = [];

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

function burstConfetti() {
  const colors = ["#156f64", "#de6d55", "#c49a38", "#315f9b", "#11131b"];

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

function renderStage() {
  $("#stageLine").textContent = shuffle(stageLines)[0];
  $("#friendTotal").textContent = friends.length;
  renderLineup();
  renderMemoryReel();
  setSpotlight(friends[spotlightIndex]);
}

function renderLineup() {
  const lineup = $("#lineupStage");
  lineup.innerHTML = "";

  friends.forEach((friend) => {
    const item = document.createElement("button");
    item.type = "button";
    item.className = `lineup-avatar ${friend.name === "samir" ? "samir-lineup" : ""}`;
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

function renderGallery(list = friends) {
  const grid = $("#friendGrid");
  grid.innerHTML = "";

  list.forEach((friend) => {
    const card = document.createElement("article");
    card.className = `friend-card ${featuredNames.includes(friend.name) ? "featured-friend" : ""} ${
      friend.name === "samir" ? "samir-feature" : ""
    }`;

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
    card.className = `match-card hidden-card ${cardData.type === "photo" ? "photo-card" : "name-card"}`;
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

function renderVibes(newRound = false) {
  if (!vibeCurrent || newRound) {
    vibeCurrent = shuffle(vibeRounds)[0];
    vibePicks = [];
    if (newRound) vibeRound += 1;
  }

  $("#vibeRound").textContent = `Round ${vibeRound}`;
  $("#vibeTitle").textContent = vibeCurrent.title;
  $("#vibeBrief").textContent = vibeCurrent.brief;
  $("#vibeMeter").style.width = `${(vibePicks.length / 3) * 100}%`;
  renderVibePicks();
  renderVibePool();
}

function renderVibePicks() {
  const picks = $("#vibePicks");
  picks.innerHTML = "";

  Array.from({ length: 3 }).forEach((_, index) => {
    const friend = vibePicks[index];
    const card = document.createElement("article");
    card.className = `vibe-pick ${friend ? "filled" : ""}`;

    if (friend) {
      const img = document.createElement("img");
      img.alt = titleName(friend.name);
      imageWithAvatarFallback(img, friend);
      card.innerHTML = `<span>Pick ${index + 1}</span><strong>${friend.name}</strong>`;
      card.append(img);
    } else {
      card.innerHTML = `<span>Pick ${index + 1}</span><strong>Choose vibe</strong>`;
    }

    picks.append(card);
  });
}

function renderVibePool() {
  const pool = $("#vibePool");
  pool.innerHTML = "";

  friends.forEach((friend) => {
    const selected = vibePicks.some((pick) => pick.name === friend.name);
    const button = document.createElement("button");
    button.type = "button";
    button.className = `vibe-option ${selected ? "selected" : ""} ${friend.name === "samir" ? "samir-option" : ""}`;
    button.disabled = selected || vibePicks.length >= 3;

    const img = document.createElement("img");
    img.alt = titleName(friend.name);
    imageWithAvatarFallback(img, friend);

    const label = document.createElement("div");
    label.innerHTML = `<strong>${friend.name}</strong><span>${friend.title}</span>`;

    button.append(img, label);
    button.addEventListener("click", () => chooseVibeFriend(friend));
    pool.append(button);
  });
}

function chooseVibeFriend(friend) {
  if (vibePicks.length >= 3) return;
  vibePicks.push(friend);

  if (vibePicks.length === 3) {
    const names = vibePicks.map((pick) => titleName(pick.name)).join(", ");
    $("#vibeBrief").textContent = `${names}: vibe locked. This crew owns the moment.`;
    burstConfetti();
  }

  $("#vibeMeter").style.width = `${(vibePicks.length / 3) * 100}%`;
  renderVibePicks();
  renderVibePool();
}

function renderAwards() {
  const grid = $("#awardGrid");
  grid.innerHTML = "";
  const shuffledFriends = shuffle(friends);

  awards.slice(0, friends.length).forEach((award, index) => {
    const friend = shuffledFriends[index];
    const card = document.createElement("article");
    card.className = `award-card ${friend.name === "samir" ? "samir-award" : ""}`;
    card.innerHTML = `<span>${award}</span><strong>${friend.name}</strong><p>${friend.note}</p>`;
    grid.append(card);
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
$("#shuffleGallery").addEventListener("click", () => renderGallery(shuffle(friends)));
$("#resetMatch").addEventListener("click", renderMatch);
$("#newVibe").addEventListener("click", () => renderVibes(true));
$("#rerollAwards").addEventListener("click", renderAwards);
document.querySelectorAll(".mood-button").forEach((button) => {
  button.addEventListener("click", () => setMood(button.dataset.mood));
});

wireTabs();
setMood("sunset");
renderStage();
renderGallery();
renderMatch();
renderVibes();
renderAwards();
